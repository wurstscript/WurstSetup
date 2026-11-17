# Keeps the Battle.net app's window out of the way while a sign-in renewal asks it to start Warcraft III.
#
# Asked to start a game, the app shows its window in front, and depending on its "when I launch a game" setting
# leaves it there. Started just before the renewal, this minimizes every app window that appears or takes the
# foreground during the next seconds, without activating it, and leaves alone a window that was already open in the
# background. With -FocusGame it brings the Warcraft III client to the front once that client has a window.
#
# Prints "ready" once it has noted the windows already open, so the caller can ask the app only then; with -Log also
# one line per action. WurstSetup embeds a copy; keep it self-contained.
param([int]$Seconds = 20, [switch]$FocusGame, [switch]$Log)
$ErrorActionPreference = "Stop"
Add-Type @"
using System; using System.Collections.Generic; using System.Runtime.InteropServices;
public static class BattleNetWindow {
  delegate bool EnumProc(IntPtr h, IntPtr p);
  [DllImport("user32.dll")] static extern bool EnumWindows(EnumProc f, IntPtr p);
  [DllImport("user32.dll")] static extern uint GetWindowThreadProcessId(IntPtr h, out uint pid);
  [DllImport("user32.dll")] static extern bool IsWindowVisible(IntPtr h);
  [DllImport("user32.dll")] static extern bool IsIconic(IntPtr h);
  [DllImport("user32.dll")] static extern IntPtr GetWindow(IntPtr h, uint cmd);
  [DllImport("user32.dll")] public static extern IntPtr GetForegroundWindow();
  [DllImport("user32.dll")] public static extern bool ShowWindowAsync(IntPtr h, int cmd);
  [DllImport("user32.dll")] public static extern bool SetForegroundWindow(IntPtr h);
  // Visible, not minimized, unowned top-level windows of the given processes.
  public static List<IntPtr> Shown(HashSet<uint> pids) {
    var found = new List<IntPtr>();
    EnumWindows((h, p) => {
      uint pid; GetWindowThreadProcessId(h, out pid);
      if (pids.Contains(pid) && IsWindowVisible(h) && !IsIconic(h) && GetWindow(h, 4) == IntPtr.Zero) found.Add(h);
      return true;
    }, IntPtr.Zero);
    return found;
  }
}
"@
function Pids([string]$name) {
  $set = New-Object 'System.Collections.Generic.HashSet[uint32]'
  Get-Process -Name $name -ErrorAction SilentlyContinue | ForEach-Object { [void]$set.Add([uint32]$_.Id) }
  return ,$set
}
$backgroundWindows = @([BattleNetWindow]::Shown((Pids "Battle.net")) | Where-Object { $_ -ne [BattleNetWindow]::GetForegroundWindow() })
# Games running already (the user's own) and those the app starts (the renewal's, closed at once) are never focused.
$notOurGame = Pids "Warcraft III"
function OurGame([uint32]$id) {
  if ($notOurGame.Contains($id)) { return $false }
  $parent = (Get-CimInstance Win32_Process -Filter "ProcessId=$id" -ErrorAction SilentlyContinue).ParentProcessId
  if ($parent -and (Get-Process -Id $parent -ErrorAction SilentlyContinue).ProcessName -eq "Battle.net") {
    [void]$notOurGame.Add($id)
    return $false
  }
  return $true
}
$watch = [Diagnostics.Stopwatch]::StartNew()
"ready"
$focused = -not $FocusGame
while ($watch.Elapsed.TotalSeconds -lt $Seconds) {
  $foreground = [BattleNetWindow]::GetForegroundWindow()
  foreach ($window in [BattleNetWindow]::Shown((Pids "Battle.net"))) {
    if ($backgroundWindows -contains $window -and $window -ne $foreground) { continue }
    # 7 = SW_SHOWMINNOACTIVE; 6 = SW_MINIMIZE, which hands the foreground on, for the window that holds it.
    [void][BattleNetWindow]::ShowWindowAsync($window, $(if ($window -eq $foreground) { 6 } else { 7 }))
    if ($Log) { "minimized {0:N1}" -f $watch.Elapsed.TotalSeconds }
  }
  if (-not $focused) {
    $ours = New-Object 'System.Collections.Generic.HashSet[uint32]'
    foreach ($id in (Pids "Warcraft III")) { if (OurGame $id) { [void]$ours.Add($id) } }
    $game = @([BattleNetWindow]::Shown($ours))
    if ($game.Count -gt 0) {
      if ($game -notcontains $foreground) { [void][BattleNetWindow]::SetForegroundWindow($game[0]) }
      $focused = $true
      if ($Log) { "focused {0:N1}" -f $watch.Elapsed.TotalSeconds }
    }
  }
  Start-Sleep -Milliseconds 50
}
