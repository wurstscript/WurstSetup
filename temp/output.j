// this script was compiled with wurst 1.8.0.0-268ba046
globals
// integer JASS_MAX_ARRAY_SIZE=0
// integer PLAYER_NEUTRAL_PASSIVE=0
// integer PLAYER_NEUTRAL_AGGRESSIVE=0
// playercolor PLAYER_COLOR_RED=null
// playercolor PLAYER_COLOR_BLUE=null
// playercolor PLAYER_COLOR_CYAN=null
// playercolor PLAYER_COLOR_PURPLE=null
// playercolor PLAYER_COLOR_YELLOW=null
// playercolor PLAYER_COLOR_ORANGE=null
// playercolor PLAYER_COLOR_GREEN=null
// playercolor PLAYER_COLOR_PINK=null
// playercolor PLAYER_COLOR_LIGHT_GRAY=null
// playercolor PLAYER_COLOR_LIGHT_BLUE=null
// playercolor PLAYER_COLOR_AQUA=null
// playercolor PLAYER_COLOR_BROWN=null
// playercolor PLAYER_COLOR_MAROON=null
// playercolor PLAYER_COLOR_NAVY=null
// playercolor PLAYER_COLOR_TURQUOISE=null
// playercolor PLAYER_COLOR_VIOLET=null
// playercolor PLAYER_COLOR_WHEAT=null
// playercolor PLAYER_COLOR_PEACH=null
// playercolor PLAYER_COLOR_MINT=null
// playercolor PLAYER_COLOR_LAVENDER=null
// playercolor PLAYER_COLOR_COAL=null
// playercolor PLAYER_COLOR_SNOW=null
// playercolor PLAYER_COLOR_EMERALD=null
// playercolor PLAYER_COLOR_PEANUT=null
// pathingtype PATHING_TYPE_WALKABILITY=null
// mapcontrol MAP_CONTROL_USER=null
// playerslotstate PLAYER_SLOT_STATE_PLAYING=null
// playerevent EVENT_PLAYER_LEAVE=null
// playerevent EVENT_PLAYER_END_CINEMATIC=null
// playerunitevent EVENT_PLAYER_UNIT_SELECTED=null
// playerunitevent EVENT_PLAYER_UNIT_ISSUED_ORDER=null
// playerunitevent EVENT_PLAYER_UNIT_ISSUED_POINT_ORDER=null
// playerunitevent EVENT_PLAYER_UNIT_ISSUED_TARGET_ORDER=null
// unitevent EVENT_UNIT_DAMAGED=null
// playerevent EVENT_PLAYER_ARROW_LEFT_DOWN=null
// playerevent EVENT_PLAYER_ARROW_LEFT_UP=null
// playerevent EVENT_PLAYER_ARROW_RIGHT_DOWN=null
// playerevent EVENT_PLAYER_ARROW_RIGHT_UP=null
// playerevent EVENT_PLAYER_ARROW_DOWN_DOWN=null
// playerevent EVENT_PLAYER_ARROW_DOWN_UP=null
// playerevent EVENT_PLAYER_ARROW_UP_DOWN=null
// playerevent EVENT_PLAYER_ARROW_UP_UP=null
// playerevent EVENT_PLAYER_MOUSE_DOWN=null
// playerevent EVENT_PLAYER_MOUSE_UP=null
// playerevent EVENT_PLAYER_MOUSE_MOVE=null
// playerunitevent EVENT_PLAYER_UNIT_SPELL_EFFECT=null
// real bj_DEGTORAD=0.
// integer bj_MAX_PLAYERS=0
// integer bj_MAX_PLAYER_SLOTS=0
// rect bj_mapInitialPlayableArea=null
real Angle_DEGTORAD=0.
real Angle_RADTODEG=0.
real Basics_ANIMATION_PERIOD=0.
integer Basics_HEIGHT_ENABLER=0
integer Basics_LOCUST_ID=0
integer Basics_GHOST_VIS_ID=0
player Basics_DUMMY_PLAYER=null
playerevent ClosureEvents_EVENT_PLAYER_CHAT_FILTER=null
trigger ClosureEvents_unitTrig=null
trigger ClosureEvents_leaveTrig=null
trigger ClosureEvents_keyTrig=null
integer array ClosureEvents_eventidToIndex
integer ClosureEvents_eventTypeCounter=0
integer EventListener_castMap=0
integer EventListener_castMapCasters=0
integer array EventListener_generalListenersFirsts
integer array EventListener_unitListenersFirsts
boolean EventListener_useMouseEvents=false
group ClosureForGroups_DUMMY_GROUP=null
integer array ClosureForGroups_tempCallbacks
integer ClosureForGroups_tempCallbacksCount=0
integer ClosureForGroups_maxCount=0
integer ClosureForGroups_iterCount=0
filterfunc ClosureForGroups_filter=null
integer array ClosureForGroups_tempCallbacksD
integer ClosureForGroups_tempCallbacksDCount=0
real ClosureForGroups_nearestDDist=0.
real ClosureForGroups_gpos_x=0.
real ClosureForGroups_gpos_y=0.
integer ClosureTimers_x=0
integer Colors_COLOR_WHITE_red=0
integer Colors_COLOR_WHITE_green=0
integer Colors_COLOR_WHITE_blue=0
integer Colors_COLOR_WHITE_alpha=0
integer array Colors_PLAYER_COLORS_red
integer array Colors_PLAYER_COLORS_green
integer array Colors_PLAYER_COLORS_blue
string array Colors_hexs
integer Colors_decs=0
real DamageDetection_SWAP_TIMEOUT=0.
boolexpr array DamageDetection_func
integer DamageDetection_funcNext=0
trigger DamageDetection_current=null
trigger DamageDetection_toDestroy=null
filterfunc DamageDetection_filter=null
boolean DamageEvent_DETECT_NATIVE_ABILITIES=false
integer DamageEvent_DAMAGE_ELEMENT_ATTACK=0
integer DamageEvent_DETECT_NATIVE_ABILITIES_ID=0
integer DamageEvent_nextDamageId=0
integer DamageEvent_nextDamageType=0
integer DamageEvent_nextDamageElement=0
boolean DamageEvent_abort=false
integer array DamageEvent_firstListeners
integer DamageEvent_maxPriority=0
integer array DamageInstance_stack
integer DamageInstance_count=0
integer DialogBox_buttonClosures=0
integer DummyRecycler_DUMMY_UNIT_ID=0
integer DummyRecycler_DIFFERENT_ANGLES=0
real DummyRecycler_ANGLE_DEGREE=0.
integer DummyRecycler_SAVED_UNITS_PER_ANGLE=0
timer DelayNode_t=null
integer DelayNode_first=0
integer array DummyRecycler_angleQueues
integer ErrorHandling_MUTE_ERROR_DURATION=0
integer ErrorHandling_PRIMARY_ERROR_KEY=0
hashtable ErrorHandling_HT=null
string ErrorHandling_lastError=null
boolean ErrorHandling_suppressErrorMessages=false
force Execute_executeForce=null
boolean array Execute_tempCallbacksSuccess
integer array Execute_tempCallbacks
integer Execute_tempCallbacksCount=0
integer ForceTests_testInt=0
timer GameTimer_gameTimer=null
real GameTimer_currentTime=0.
string GamecacheKeys_GC_KEYS=null
integer GamecacheKeys_GC_KEYS_LENGTH=0
string array GamecacheKeys_keys
integer GamecacheKeys_count=0
integer GroupUtils_GROUP_NUMBER_LIMIT=0
integer GroupUtils_START_CREATE_GROUPS=0
integer GroupUtils_used=0
group array GroupUtils_stack
integer GroupUtils_numStack=0
integer GroupUtils_numTotal=0
group GroupUtils_iterGroup=null
boolean GroupUtils_shownMaxError=false
group Group_ENUM_GROUP=null
integer Group_unitCounter=0
integer Group_randomCounter=0
group Group_iterGroup=null
hashtable HashList_ht=null
hashtable HashList_occurences=null
hashtable HashSet_position=null
timer TimedIOTaskExecutor_updater=null
integer TimedIOTaskExecutor_runningCount=0
boolean TimedIOTaskExecutor_timerStarted=false
integer TimedIOTaskExecutor_LinkedListModule_first=0
integer TimedIOTaskExecutor_LinkedListModule_last=0
integer TimedIOTaskExecutor_LinkedListModule_size=0
integer InstantDummyCaster_DUMMY_CASTER_UNIT_ID=0
integer Integer_INT_MAX=0
integer Integer_INT_MIN=0
real Knockback3_DESTRUCTABLE_ENUM_SIZE=0.
boolean Knockback3_USE_MOVE_SPEED_MODIFIERS=false
boolean Knockback3_USE_PROP_WINDOW_MODIFIERS=false
real Knockback3_restitutionCoefficientGround=0.
real Knockback3_restitutionCoefficientDestructable=0.
real Knockback3_frictionCoefficientGround=0.
real Knockback3_gravity=0.
real Knockback3_elasticityThreshold=0.
real Knockback3_airborneThreshold=0.
real Knockback3_isAirborneThreshold=0.
real Knockback3_minimumSlideSpeed=0.
real Knockback3_frictionFxThreshold=0.
string Knockback3_frictionFxPath=null
real Knockback3_destroyDestructableSpeedThreshold=0.
real Knockback3_destroyDestructableHeightThreshold=0.
boolean Knockback3_hitDestructable=false
rect Knockback3_destructableRect=null
integer Knockback3_unitNodes=0
timer Knockback3_clock=null
integer Knockback3_LinkedListModule_first=0
integer Knockback3_LinkedListModule_last=0
integer Knockback3_LinkedListModule_size=0
integer Knockback3_LinkedListModule_staticItr=0
integer LastOrder_ORDERS_TO_HOLD=0
integer array LastOrder_lastOrder
integer TestClass_LinkedListModule_first=0
integer TestClass_LinkedListModule_last=0
integer LocalFiles_callbacks=0
boolean MagicFunctions_compiletime=false
rect MapBounds_playableMapRect=null
rect MapBounds_boundRect=null
region MapBounds_boundRegion=null
real MapBounds_playableMin_x=0.
real MapBounds_playableMin_y=0.
real MapBounds_playableMax_x=0.
real MapBounds_playableMax_y=0.
real MapBounds_boundMin_x=0.
real MapBounds_boundMin_y=0.
real MapBounds_boundMax_x=0.
real MapBounds_boundMax_y=0.
gamecache MetadataStore_META_CACHE=null
string MetadataStore_META_INTEGER_KEY=null
string MetadataStore_META_REAL_KEY=null
string MetadataStore_META_BOOLEAN_KEY=null
string MetadataStore_META_STRING_KEY=null
string MetadataStore_META_SYNC_ROUNDS_KEY=null
integer MetadataStore_META_COUNT=0
integer NetworkConfig_CHARS_PER_ENCODE_DECODE=0
integer NetworkConfig_DATA_PER_EXECUTE=0
integer NetworkConfig_MAX_CHARACTERS_PER_KEY=0
gamecache Network_DATA_CACHE=null
gamecache Network_STRING_CACHE=null
string ObjectIds_CHARMAP=null
string Objects_impaleTargetDust=null
trigger OnUnitEnterLeave_eventTrigger=null
group OnUnitEnterLeave_preplacedUnits=null
unit array OnUnitEnterLeave_tempUnits
integer OnUnitEnterLeave_tempUnitsCount=0
integer OnUnitEnterLeave_ABILITY_ID=0
string OrderedStringBuffer_TERMINATOR=null
player array Player_players
player Player_localPlayer=null
playercolor Playercolor_PLAYER_COLOR_BLACK_AGGRESSIVE=null
playercolor Playercolor_PLAYER_COLOR_UNKNOWN1=null
playercolor Playercolor_PLAYER_COLOR_UNKNOWN2=null
playercolor Playercolor_PLAYER_COLOR_BLACK_PASSIVE=null
integer PreloadIO_PACKETS_PER_FILE=0
integer PreloadIO_MAX_PACKET_LENGTH=0
string array IOReader_playerNames
string array IOReader_packets
integer IOReader_packetNumber=0
integer IOReader_packetCount=0
string IOWriter_DATA_PADDING_1=null
string IOWriter_DATA_PADDING_2=null
string IOWriter_DATA_PADDING_3=null
string IOWriter_DATA_FOOTER=null
integer IOWriter_packetNumber=0
boolean Preloader_autoFinish=false
group Preloader_dumg=null
unit Preloader_dum=null
integer Printing_DEBUG_LEVEL=0
real Printing_DEBUG_MSG_DURATION=0.
real Real_REAL_MAX=0.
trigger array RegisterEvents_t
integer RegisterEvents_onCastMap=0
boolean SafetyChecks_SAFETY_CHECKS_ENABLED=false
real Simulate3dSound_PERIOD=0.
integer array DynamicSound_TimedLoop_instances
integer DynamicSound_TimedLoop_instanceCount=0
triggercondition DynamicSound_TimedLoop_triggerCond=null
integer AbstractStringBuffer_MAX_BACKBUFFER_SIZE=0
integer AbstractStringBuffer_MAX_BUFFER_SIZE=0
string StringEncoder_STRING_ALPHABET=null
integer StringEncoder_STRING_ALPHABET_LENGTH=0
integer StringEncoder_alphabetLookup=0
string array StringUtils_c2s
integer StringUtils_MAX_INDEX=0
string String_charset=null
string String_numberset=null
unit SyncSimple_last=null
integer SyncSimple_count=0
integer array SimpleSynchronizer_reverseLookup
integer SimpleSynchronizer_allPlayers_val=0
integer SimpleSynchronizer_LinkedListModule_first=0
integer SimpleSynchronizer_LinkedListModule_last=0
integer SimpleSynchronizer_LinkedListModule_size=0
hashtable Table_ht=null
real TerrainUtils_MAX_RANGE_SQ=0.
integer TerrainUtils_DUMMY_ITEM_ID=0
item TerrainUtils_dummyItem=null
rect TerrainUtils_itemSearchRect=null
item array TerrainUtils_hiddenItems
integer TerrainUtils_hiddenItemsCount=0
integer TerrainUtils_TILES_X=0
integer TerrainUtils_TILES_Y=0
timer array TimerUtils_freeTimers
integer TimerUtils_freeTimersCount=0
integer TimerUtils_timerData=0
integer TimerUtils_HELD=0
timer TimerUtils_timedLoopTimer=null
trigger TimerUtils_timedLoopTrig=null
integer TimerUtils_conditionCount=0
integer TypeCasting_typecastdata=0
real TypeCasting_R2I_PRECISION=0.
trigger UnitIndexer_onIndexTrigger=null
trigger UnitIndexer_onDeindexTrigger=null
unit array UnitIndexer_tempUnits
integer UnitIndexer_tempUnitsCount=0
real Vectors_ZERO2_x=0.
real Vectors_ZERO2_y=0.
real Vectors_ZERO3_x=0.
real Vectors_ZERO3_y=0.
real Vectors_ZERO3_z=0.
location Vectors_tempLoc=null
integer array LimitedExecuteCondition_nextFree
integer LimitedExecuteCondition_firstFree=0
integer LimitedExecuteCondition_maxIndex=0
integer array LimitedExecuteCondition_typeId
integer array LimitedExecuteAction_nextFree
integer LimitedExecuteAction_firstFree=0
integer LimitedExecuteAction_maxIndex=0
integer array LimitedExecuteAction_typeId
integer array Buffer_nextFree
integer Buffer_firstFree=0
integer Buffer_maxIndex=0
integer array Buffer_typeId
integer array BufferSerializable_nextFree
integer BufferSerializable_firstFree=0
integer BufferSerializable_maxIndex=0
integer array BufferSerializable_typeId
integer OnCastListener_firstFree=0
integer array OnCastListener_typeId
integer array CallbackSingle_nextFree
integer CallbackSingle_firstFree=0
integer CallbackSingle_maxIndex=0
integer array CallbackSingle_typeId
integer array EventListener_nextFree
integer EventListener_firstFree=0
integer EventListener_maxIndex=0
integer array EventListener_typeId
integer array ForGroupCallback_nextFree
integer ForGroupCallback_firstFree=0
integer ForGroupCallback_maxIndex=0
integer array ForGroupCallback_typeId
integer CallbackCounted_firstFree=0
integer array CallbackCounted_typeId
integer array DamageElement_nextFree
integer DamageElement_firstFree=0
integer DamageElement_maxIndex=0
integer array DamageInstance_nextFree
integer DamageInstance_firstFree=0
integer DamageInstance_maxIndex=0
integer array DamageInstance_typeId
integer DummyCaster_firstFree=0
integer array DummyCaster_typeId
integer array ArrayQueue_nextFree
integer ArrayQueue_firstFree=0
integer ArrayQueue_maxIndex=0
integer array ArrayQueue_typeId
integer DelayNode_firstFree=0
integer array DelayNode_typeId
integer array ForForceCallback_nextFree
integer ForForceCallback_firstFree=0
integer ForForceCallback_maxIndex=0
integer array ForForceCallback_typeId
integer GamecacheBuffer_firstFree=0
integer array GamecacheBuffer_typeId
integer array SynchronizerFunction_nextFree
integer SynchronizerFunction_firstFree=0
integer SynchronizerFunction_maxIndex=0
integer array SynchronizerFunction_typeId
integer HashList_firstFree=0
integer array HashList_typeId
integer array Table_nextFree
integer Table_firstFree=0
integer Table_maxIndex=0
integer array Table_typeId
integer IOTaskExecutor_firstFree=0
integer array IOTaskExecutor_typeId
integer array IOTask_nextFree
integer IOTask_firstFree=0
integer IOTask_maxIndex=0
integer array IOTask_typeId
integer array BackIterator_nextFree
integer BackIterator_firstFree=0
integer BackIterator_maxIndex=0
integer array Iterator_nextFree
integer Iterator_firstFree=0
integer Iterator_maxIndex=0
integer array Iterator_typeId
integer Knockback3_firstFree=0
integer array Knockback3_typeId
integer array BackIterator_nextFree_35
integer BackIterator_firstFree_27=0
integer BackIterator_maxIndex_31=0
integer array Iterator_nextFree_278
integer Iterator_firstFree_270=0
integer Iterator_maxIndex_274=0
integer array Iterator_typeId_282
integer array Order_nextFree
integer Order_firstFree=0
integer Order_maxIndex=0
integer array Order_typeId
integer array BackIterator_nextFree_36
integer BackIterator_firstFree_28=0
integer BackIterator_maxIndex_32=0
integer array Iterator_nextFree_279
integer Iterator_firstFree_271=0
integer Iterator_maxIndex_275=0
integer array Comparator_nextFree
integer Comparator_firstFree=0
integer Comparator_maxIndex=0
integer LLBackIterator_firstFree=0
integer array LLBackIterator_typeId
integer array LLEntry_nextFree
integer LLEntry_firstFree=0
integer LLEntry_maxIndex=0
integer array LLEntry_typeId
integer array LLIterator_nextFree
integer LLIterator_firstFree=0
integer LLIterator_maxIndex=0
integer array LLIterator_typeId
integer array LinkedList_nextFree
integer LinkedList_firstFree=0
integer LinkedList_maxIndex=0
integer array LinkedList_typeId
integer MetadataStore_firstFree=0
integer array MetadataStore_typeId
integer AbstractFile_firstFree=0
integer array AbstractFile_typeId
integer array FileLoadCallback_typeId
integer array FileSaveCallback_typeId
integer Network_firstFree=0
integer array Network_typeId
integer array NetworkFinishedCallback_nextFree
integer NetworkFinishedCallback_firstFree=0
integer NetworkFinishedCallback_maxIndex=0
integer array NetworkFinishedCallback_typeId
integer array SynchronizationCallback_nextFree
integer SynchronizationCallback_firstFree=0
integer SynchronizationCallback_maxIndex=0
integer array SynchronizationCallback_typeId
integer array IdGenerator_nextFree
integer IdGenerator_firstFree=0
integer IdGenerator_maxIndex=0
integer array OrderStringFactory_nextFree
integer OrderStringFactory_firstFree=0
integer OrderStringFactory_maxIndex=0
integer array PersistentStore_typeId
integer Sim3DSound_firstFree=0
integer array Sim3DSound_typeId
integer DynamicSound_firstFree=0
integer array DynamicSound_typeId
integer SoundInstance_firstFree=0
integer array SoundInstance_typeId
integer array ChunkElement_nextFree
integer ChunkElement_firstFree=0
integer ChunkElement_maxIndex=0
integer array ChunkElement_typeId
integer StringEncoder_firstFree=0
integer array StringEncoder_typeId
integer array SimpleSynchronizer_nextFree
integer SimpleSynchronizer_firstFree=0
integer SimpleSynchronizer_maxIndex=0
integer array SimpleSynchronizer_typeId
integer array BackIterator_nextFree_37
integer BackIterator_firstFree_29=0
integer BackIterator_maxIndex_33=0
integer array Iterator_nextFree_280
integer Iterator_firstFree_272=0
integer Iterator_maxIndex_276=0
integer array Iterator_typeId_283
integer array UnitIndex_nextFree
integer UnitIndex_firstFree=0
integer UnitIndex_maxIndex=0
integer array UnitIndex_typeId
integer array this
integer array this_790
integer array sink
integer array reader
integer array this_791
integer array reader_660
integer array buffer
integer array this_792
integer array buffer_611
integer array AbstractBuffer_mode
integer array AbstractBuffer_failureMode
integer array SerializableElement_id
integer array SerializableElement_value
string array TestSerializable_name
integer array TestSerializable_id
real array TestSerializable_facing
integer array TestSerializable_elements
integer array EventListener_eventId
integer array EventListener_next
integer array EventListener_prev
integer array OnCastListener_next
integer array OnCastListener_prev
integer array OnCastListener_abilId
unit array OnCastListener_eventUnit
integer array CallbackCounted_count
timer array CallbackCounted_t
timer array CallbackSingle_t
real array DamageInstance_amount
integer array DamageListener_next
integer array DummyCaster_castCount
unit array dummy
integer array id
integer array this_795
integer array ArrayQueue_rp
integer array ArrayQueue_size
unit array DelayNode_u
real array DelayNode_delayTime
integer array DelayNode_next
integer array condition
integer array resetCount
integer array action
gamecache array GamecacheBuffer_gc
string array GamecacheBuffer_parentKey
integer array GamecacheBuffer_writeIndex
integer array GamecacheBuffer_readIndex
integer array GamecacheBuffer_bufferType
integer array GamecacheBuffer_syncCounter
integer array this_808
integer array this_809
integer array this_810
integer array this_811
integer array synchronizer_788
integer array this_812
integer array i_649
string array prefix
integer array i
integer array depth
integer array createNow
integer array HashBuffer_buffer
integer array HashBuffer_integerWriteIndex
integer array HashBuffer_realWriteIndex
integer array HashBuffer_booleanWriteIndex
integer array HashBuffer_stringWriteIndex
integer array HashBuffer_integerReadIndex
integer array HashBuffer_realReadIndex
integer array HashBuffer_booleanReadIndex
integer array HashBuffer_stringReadIndex
integer array HashList_size
integer array HashMap_size
integer array IterableMap_keys
boolean array IterableMap__destroyed
integer array AbstractIOTaskExecutor_taskQueue
integer array AbstractIOTaskExecutor_onCompleteTask
boolean array AbstractIOTaskExecutor_finished
integer array this_801
integer array this_802
real array TimedIOTaskExecutor_delay
real array TimedIOTaskExecutor_lastExecution
integer array TimedIOTaskExecutor_LinkedListModule_prev
integer array TimedIOTaskExecutor_LinkedListModule_next
integer array Iterator_current
boolean array Iterator_destroyOnClose
unit array Knockback3_u
real array Knockback3_del_x
real array Knockback3_del_y
real array Knockback3_del_z
integer array Knockback3_LinkedListModule_prev
integer array Knockback3_LinkedListModule_next
integer array Iterator_current_265
integer array Order_prev
unit array Order_orderedUnit
integer array result
integer array LLEntry_elem
integer array LLEntry_prev
integer array LLEntry_next
integer array LLIterator_dummy
integer array LLIterator_current
integer array LLIterator_parent
boolean array LLIterator_destroyOnClose
integer array LinkedList_dummy
integer array LinkedList_size
integer array LinkedList_staticItr
integer array LinkedList_staticBackItr
string array MetadataStore_mkey
string array AbstractFile_TERMINATOR
integer array AbstractFile_buffer
boolean array AbstractFile_multiMode
string array AbstractFile_path
integer array AbstractFile_executor
integer array this_806
integer array chunkId_624
integer array this_807
integer array callback_621
integer array this_803
integer array chunkId
integer array this_804
integer array chunkId_623
integer array this_805
integer array callback_620
integer array Network_currentState
string array Network_mkey
integer array Network_dataBuffer
integer array Network_stringEncoder
integer array Network_gcIntBuffer
integer array Network_gcRealBuffer
integer array Network_gcBooleanBuffer
integer array Network_gcStringBuffer
integer array Network_metadataStore
player array Network_sender
integer array Network_meta_ints
integer array Network_meta_reals
integer array Network_meta_booleans
integer array Network_meta_asciiInts
integer array Network_meta_syncs
boolean array Network_metaReceived
integer array Network_counters_ints
integer array Network_counters_reals
integer array Network_counters_booleans
integer array Network_counters_asciiInts
integer array Network_counters_syncs
integer array Network_finishCallback
integer array this_813
integer array this_814
integer array this_815
integer array this_816
integer array this_817
integer array this_818
integer array this_819
integer array this_820
integer array this_821
integer array this_822
integer array this_823
integer array this_824
integer array this_825
integer array this_826
integer array this_827
integer array this_828
integer array this_829
integer array this_830
integer array this_831
integer array this_832
integer array this_833
integer array this_834
integer array this_793
integer array this_794
integer array buffer_612
player array PersistentStore_owner
integer array PersistentStore_entity
integer array network
integer array reader_661
integer array synchronizer
integer array buffer_613
integer array reader_662
integer array buffer_614
integer array network_656
integer array reader_663
integer array network_657
integer array this_796
integer array callback
integer array this_797
integer array callback_618
integer array this_798
integer array buffer_615
integer array buffer_616
integer array this_799
integer array synchronizer_787
integer array this_800
integer array writer
integer array callback_619
sound array Sim3DSound_snd
unit array Sim3DSound_sourceUnit
real array Sim3DSound_source_x
real array Sim3DSound_source_y
boolean array Sim3DSound_adjust
real array Sim3DSound_initial
real array Sim3DSound_factor
real array Sim3DSound_dur
real array Sim3DSound_maxD
real array Sim3DSound_distCut
real array DynamicSound_targetPitch
real array DynamicSound_currentPitch
real array DynamicSound_smoothness
real array DynamicSound_minimumLength
integer array DynamicSound_soundData
integer array DynamicSound_TimedLoop_mode
integer array SoundDefinition_soundStack
integer array SoundDefinition_duration
real array SoundInstance_pos_x
real array SoundInstance_pos_y
real array SoundInstance_pos_z
sound array SoundInstance_snd
integer array SoundInstance_soundDef
unit array SoundInstance_onUnit
player array SoundInstance_p
integer array SoundInstance_s3s
integer array AbstractStringBuffer_chunks
string array AbstractStringBuffer_readBuffer
string array AbstractStringBuffer_writeBuffer
integer array AbstractStringBuffer_maxBufferSize
string array ChunkElement_content
integer array StringEncoder_asciiIntCount
integer array StringEncoder_stringCount
string array StringEncoder_currentValue
integer array StringEncoder_counter
integer array StringEncoder_buffer
integer array this_835
integer array length
integer array this_836
string array value
integer array this_837
integer array length_654
integer array this_838
integer array this_839
integer array this_840
integer array this_841
integer array this_842
unit array SimpleSynchronizer_dummy
integer array SimpleSynchronizer_syncedPlayers_val
integer array SimpleSynchronizer_callback
integer array SimpleSynchronizer_LinkedListModule_prev
integer array SimpleSynchronizer_LinkedListModule_next
integer array Iterator_current_266
boolean array Iterator_destroyOnClose_268
unit array UnitIndex__unit
real real_asAngleDegrees_return_radians=0.
real real_fromDeg_return_radians=0.
real angle_op_mult_return_radians=0.
integer emptyBitset_return_val=0
integer bitset_add_return_val=0
real vec2_withZ_return_x=0.
real vec2_withZ_return_y=0.
real vec2_withZ_return_z=0.
real vec3_toVec2_return_x=0.
real vec3_toVec2_return_y=0.
real unit_getPos_return_x=0.
real unit_getPos_return_y=0.
real destructable_getPos_return_x=0.
real destructable_getPos_return_y=0.
real getSpellTargetPos_return_x=0.
real getSpellTargetPos_return_y=0.
real getOrderPos_return_x=0.
real getOrderPos_return_y=0.
real widget_getPos_return_x=0.
real widget_getPos_return_y=0.
real getOrderTargetPos_return_x=0.
real getOrderTargetPos_return_y=0.
real vec2_toVec3_return_x=0.
real vec2_toVec3_return_y=0.
real vec2_toVec3_return_z=0.
real item_getPos_return_x=0.
real item_getPos_return_y=0.
real vec3_op_plus_return_x=0.
real vec3_op_plus_return_y=0.
real vec3_op_plus_return_z=0.
real unit_getPos3Real_return_x=0.
real unit_getPos3Real_return_y=0.
real unit_getPos3Real_return_z=0.
real vec3_op_mult_return_x=0.
real vec3_op_mult_return_y=0.
real vec3_op_mult_return_z=0.
real unit_getDefaultPropWindow_return_radians=0.
real vec3_project_return_x=0.
real vec3_project_return_y=0.
real vec3_project_return_z=0.
real vec2_op_plus_return_x=0.
real vec2_op_plus_return_y=0.
real vec2_op_mult_return_x=0.
real vec2_op_mult_return_y=0.
real vec2_withRealZ_return_x=0.
real vec2_withRealZ_return_y=0.
real vec2_withRealZ_return_z=0.
integer MetadataStore_getCounts_return_ints=0
integer MetadataStore_getCounts_return_reals=0
integer MetadataStore_getCounts_return_booleans=0
integer MetadataStore_getCounts_return_asciiInts=0
integer MetadataStore_getCounts_return_syncs=0
integer dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_ints=0
integer dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_reals=0
integer dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_booleans=0
integer dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_asciiInts=0
integer dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_syncs=0
code ref_function_init_Abilities=null
code ref_function_init_AbilityIds=null
code ref_function_init_Real=null
code ref_function_init_Integer=null
code ref_function_init_Angle=null
code ref_function_init_String=null
code ref_function_init_Vectors=null
code ref_function_init_Player=null
code ref_function_init_Basics=null
code ref_function_init_Maths=null
code ref_function_init_Printing=null
code ref_function_init_GameTimer=null
code ref_function_init_MagicFunctions=null
code ref_function_init_ErrorHandling=null
code ref_function_init_Matrices=null
code ref_function_init_Quaternion=null
code ref_function_init_Table=null
code ref_function_init_Playercolor=null
code ref_function_init_Colors=null
code ref_function_init_Group=null
code ref_function_init_Lightning=null
code ref_function_init_WeatherEffects=null
code ref_function_init_TypeCasting=null
code ref_function_init_BigNum=null
code ref_function_init_BitwiseInit=null
code ref_function_init_Buildings=null
code ref_function_init_TargetsAllowed=null
code ref_function_init_Doodads=null
code ref_function_init_Icons=null
code ref_function_init_Objects=null
code ref_function_init_Sounds=null
code ref_function_init_Soundsets=null
code ref_function_init_Textures=null
code ref_function_init_UI=null
code ref_function_init_Units=null
code ref_function_init_UnitIds=null
code ref_function_init_ClosureForGroups=null
code ref_function_init_LinkedList=null
code ref_function_init_StringUtils=null
code ref_function_init_ObjectIds=null
code ref_function_init_MapBounds=null
code ref_function_init_DummyRecycler=null
code ref_function_init_TimerUtils=null
code ref_function_init_ClosureTimers=null
code ref_function_init_Preloader=null
code ref_function_init_ObjectIdGenerator=null
code ref_function_init_ChannelAbilityPreset=null
code ref_function_init_HashList=null
code ref_function_init_EventHelper=null
code ref_function_init_RegisterEvents=null
code ref_function_init_OnUnitEnterLeave=null
code ref_function_init_UnitIndexer=null
code ref_function_init_ClosureEvents=null
code ref_function_init_DamageDetection=null
code ref_function_init_DamageEvent=null
code ref_function_init_DebugFile=null
code ref_function_init_DialogBox=null
code ref_function_init_Execute=null
code ref_function_init_ForceTests=null
code ref_function_init_NetworkConfig=null
code ref_function_init_GamecacheKeys=null
code ref_function_init_GroupUtils=null
code ref_function_init_HashSet=null
code ref_function_init_IOTaskExecutor=null
code ref_function_init_InstantDummyCaster=null
code ref_function_init_TerrainUtils=null
code ref_function_init_Knockback3=null
code ref_function_init_LastOrder=null
code ref_function_init_LinkedListModuleTests=null
code ref_function_init_LocalFiles=null
code ref_function_init_Metadata=null
code ref_function_init_SyncSimple=null
code ref_function_init_SafetyChecks=null
code ref_function_init_StringBuffer=null
code ref_function_init_OrderedStringBuffer=null
code ref_function_init_StringEncoder=null
code ref_function_init_Network=null
code ref_function_init_Orders=null
code ref_function_init_PreloadIO=null
code ref_function_init_Persistable=null
code ref_function_init_Raycast=null
code ref_function_init_Simulate3dSound=null
code ref_function_init_SoundUtils=null
code ref_function_init_StandardTextTags=null
code ref_function_init_Tiles=null
code ref_function_init_UpgradeObjEditing=null
code ref_function_code__onUnitIndex_ClosureEvents=null
code ref_function_code__onUnitDeindex_ClosureEvents=null
code ref_function_EventListener_generalEventCallback=null
code ref_function_code__Filter_ClosureForGroups=null
code ref_function_code__Filter_DamageDetection=null
code ref_function_code__onEnter_DamageDetection=null
code ref_function_swap=null
code ref_function_code__onEnter_DamageEvent=null
code ref_function_DelayNode_recycle=null
code ref_function_executeCurrentCallback=null
code ref_function_code__startPeriodic_GameTimer=null
code ref_function_TimedIOTaskExecutor_updateInstances=null
code ref_function_code__EnumDestructablesInRect_Knockback3_Knockback3=null
code ref_function_actions=null
code ref_function_spellActions=null
code ref_function_code__onUnitDeindex_LastOrder=null
code ref_function_code__registerPlayerUnitEvent_RegisterEvents=null
code ref_function_DynamicSound_TimedLoop_onExpire=null
code ref_function_SoundDefinition_recycle=null
code ref_function_code__onLeave_SyncSimple=null
code ref_function_code__EnumItemsInRect_TerrainUtils=null
code ref_function_code__onEnter_UnitIndexer=null
code ref_function_code__onLeave_UnitIndexer=null
code ref_function_code__addAction_nullTimer_ClosureEvents=null
code ref_function_code__addAction_nullTimer_ClosureEvents_679=null
code ref_function_code__addAction_nullTimer_ClosureEvents_680=null
code ref_function_code__registerPlayerUnitEvent_nullTimer_ClosureEvents=null
code ref_function_code__start_CallbackSingle_ClosureTimers=null
code ref_function_code__Filter_registerEnterRegion_nullTimer_OnUnitEnterLeave=null
code ref_function_code__registerPlayerUnitEvent_nullTimer_OnUnitEnterLeave=null
code ref_function_code__ForGroup_nullTimer_OnUnitEnterLeave=null
unit createDummytempReturn=null
effect addEffecttempReturn=null
unit group_nexttempReturn=null
unit getDummytempReturn=null
timer getTimertempReturn=null
trigger dispatch_Table_Table_Table_loadTriggertempReturn=null
endglobals
function Loglevel_getTag takes integer this_2 returns string
	local integer temp = this_2
	if temp == 0 then
		return "|cffADADADtrace|r"
	elseif temp == 1 then
		return "|cff2685DCdebug|r"
	elseif temp == 2 then
		return "|cffFFCC00info|r"
	elseif temp == 3 then
		return "|cffF47E3Ewarning|r"
	else
		return "|cffFB2700error|r"
	endif
endfunction

function printLog takes player showTo, integer loglvl, string msg returns nothing
	local string compositeMsg
	if Printing_DEBUG_LEVEL <= loglvl then
		set compositeMsg = Loglevel_getTag(loglvl) + " - " + msg
		call DisplayTimedTextToPlayer(showTo, 0., 0., Printing_DEBUG_MSG_DURATION, compositeMsg)
	endif
endfunction

function Log_error takes string msg returns nothing
	call printLog(Player_localPlayer, 4, msg)
endfunction

function compileError takes string message returns nothing
endfunction

function hashtable_hasBoolean takes hashtable this_2, integer parentKey, integer childKey returns boolean
	return HaveSavedBoolean(this_2, parentKey, childKey)
endfunction

function hashtable_hasInt takes hashtable this_2, integer parentKey, integer childKey returns boolean
	return HaveSavedInteger(this_2, parentKey, childKey)
endfunction

function hashtable_loadBoolean takes hashtable this_2, integer parentKey, integer childKey returns boolean
	return LoadBoolean(this_2, parentKey, childKey)
endfunction

function hashtable_loadInt takes hashtable this_2, integer parentKey, integer childKey returns integer
	return LoadInteger(this_2, parentKey, childKey)
endfunction

function hashtable_saveBoolean takes hashtable this_2, integer parentKey, integer childKey, boolean value_2 returns nothing
	call SaveBoolean(this_2, parentKey, childKey, value_2)
endfunction

function hashtable_saveInt takes hashtable this_2, integer parentKey, integer childKey, integer value_2 returns nothing
	call SaveInteger(this_2, parentKey, childKey, value_2)
endfunction

function real_toInt takes real this_2 returns integer
	return R2I(this_2)
endfunction

function string_getHash takes string this_2 returns integer
	return StringHash(this_2)
endfunction

function error takes string msg returns nothing
	local integer hash
	if MagicFunctions_compiletime then
		call compileError("ERROR: " + msg)
	else
		if  not ErrorHandling_suppressErrorMessages then
			set hash = string_getHash(msg)
			if hashtable_hasInt(ErrorHandling_HT, ErrorHandling_PRIMARY_ERROR_KEY, hash) then
				if hashtable_loadInt(ErrorHandling_HT, ErrorHandling_PRIMARY_ERROR_KEY, hash) + ErrorHandling_MUTE_ERROR_DURATION < GameTimer_currentTime then
					call Log_error(msg + "")
					call hashtable_saveInt(ErrorHandling_HT, ErrorHandling_PRIMARY_ERROR_KEY, hash, real_toInt(GameTimer_currentTime))
					call hashtable_saveBoolean(ErrorHandling_HT, ErrorHandling_PRIMARY_ERROR_KEY, hash, false)
				elseif hashtable_hasBoolean(ErrorHandling_HT, ErrorHandling_PRIMARY_ERROR_KEY, hash) then
					if  not hashtable_loadBoolean(ErrorHandling_HT, ErrorHandling_PRIMARY_ERROR_KEY, hash) then
						call Log_error("|cffFF3A29Excessive repeating errors are being omitted")
						call hashtable_saveBoolean(ErrorHandling_HT, ErrorHandling_PRIMARY_ERROR_KEY, hash, true)
					endif
				else
					call Log_error("|cffFF3A29Excessive repeating errors are being omitted")
					call hashtable_saveBoolean(ErrorHandling_HT, ErrorHandling_PRIMARY_ERROR_KEY, hash, true)
				endif
			else
				call hashtable_saveInt(ErrorHandling_HT, ErrorHandling_PRIMARY_ERROR_KEY, hash, real_toInt(GameTimer_currentTime))
				call Log_error("Message: " + msg + "")
			endif
		endif
		set ErrorHandling_lastError = msg
		call I2S(1 / 0)
	endif
endfunction

function ArrayQueue_units_set takes integer instanceId, integer arrayIndex, unit value_2 returns nothing
	if arrayIndex < 0 or arrayIndex >= 6 then
		call error("Index out of Bounds")
	elseif arrayIndex <= 2 then
		if arrayIndex <= 1 then
			if arrayIndex <= 0 then
			endif
		endif
	elseif arrayIndex <= 4 then
		if arrayIndex <= 3 then
		endif
	endif
endfunction

function ArrayQueue_enqueue takes integer this_2, unit u returns nothing
	if ArrayQueue_size[this_2] < DummyRecycler_SAVED_UNITS_PER_ANGLE then
		set ArrayQueue_size[this_2] = ArrayQueue_size[this_2] + 1
		set ArrayQueue_rp[this_2] = ModuloInteger(ArrayQueue_rp[this_2] + 1, DummyRecycler_SAVED_UNITS_PER_ANGLE)
		call ArrayQueue_units_set(this_2, ArrayQueue_rp[this_2], u)
	else
		call error("Queue Overflow")
	endif
endfunction

function dispatch_ArrayQueue_DummyRecycler_ArrayQueue_enqueue takes integer this_2, unit u returns nothing
	if ArrayQueue_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling ArrayQueue.enqueue")
		else
			call error("Called ArrayQueue.enqueue on invalid object.")
		endif
	endif
	call ArrayQueue_enqueue(this_2, u)
endfunction

function real_asAngleDegrees takes real this_2 returns real
	set real_asAngleDegrees_return_radians = this_2 * Angle_DEGTORAD
	return real_asAngleDegrees_return_radians
endfunction

function unit_pause takes unit this_2 returns nothing
	call PauseUnit(this_2, true)
endfunction

function unit_remove takes unit this_2 returns nothing
	call RemoveUnit(this_2)
endfunction

function angle_degrees takes real this_radians returns real
	return this_radians * Angle_RADTODEG
endfunction

function unit_setFacing takes unit this_2, real a_radians returns nothing
	call SetUnitFacing(this_2, angle_degrees(a_radians))
endfunction

function unit_setScale takes unit this_2, real scale returns nothing
	call SetUnitScale(this_2, scale, scale, scale)
endfunction

function unit_setVertexColor takes unit this_2, integer col_red, integer col_green, integer col_blue, integer col_alpha returns nothing
	call SetUnitVertexColor(this_2, col_red, col_green, col_blue, col_alpha)
endfunction

function unit_setX takes unit this_2, real x returns nothing
	call SetUnitX(this_2, x)
endfunction

function unit_setY takes unit this_2, real y returns nothing
	call SetUnitY(this_2, y)
endfunction

function unit_setXY takes unit this_2, real pos_x, real pos_y returns nothing
	local unit receiver = this_2
	local unit receiver_2
	call unit_setX(receiver, pos_x)
	set receiver_2 = receiver
	call unit_setY(receiver_2, pos_y)
	set receiver = null
	set receiver_2 = null
endfunction

function DummyRecycler_recycle takes unit u returns nothing
	local integer smallestQueue = 0
	local integer i_2 = 1
	local integer temp = DummyRecycler_DIFFERENT_ANGLES - 1
	local unit receiver
	local unit receiver_2
	local unit receiver_3
	local unit receiver_4
	local unit receiver_5
	loop
		exitwhen i_2 > temp
		if ArrayQueue_size[DummyRecycler_angleQueues[smallestQueue]] > ArrayQueue_size[DummyRecycler_angleQueues[i_2]] then
			set smallestQueue = i_2
		endif
		set i_2 = i_2 + 1
	endloop
	if ArrayQueue_size[DummyRecycler_angleQueues[smallestQueue]] >= DummyRecycler_SAVED_UNITS_PER_ANGLE then
		call unit_remove(u)
	else
		call dispatch_ArrayQueue_DummyRecycler_ArrayQueue_enqueue(DummyRecycler_angleQueues[smallestQueue], u)
		set receiver = u
		call unit_setXY(receiver, MapBounds_boundMax_x, MapBounds_boundMax_y)
		set receiver_2 = receiver
		call unit_pause(receiver_2)
		set receiver_3 = receiver_2
		call unit_setFacing(receiver_3, real_asAngleDegrees(smallestQueue * DummyRecycler_ANGLE_DEGREE))
		set receiver_4 = receiver_3
		call unit_setScale(receiver_4, 1.)
		set receiver_5 = receiver_4
		call unit_setVertexColor(receiver_5, Colors_COLOR_WHITE_red, Colors_COLOR_WHITE_green, Colors_COLOR_WHITE_blue, Colors_COLOR_WHITE_alpha)
	endif
	set receiver = null
	set receiver_2 = null
	set receiver_3 = null
	set receiver_4 = null
	set receiver_5 = null
endfunction

function DelayNode_onDestroy takes integer this_2 returns nothing
endfunction

function dealloc_DelayNode takes integer obj returns nothing
	if DelayNode_typeId[obj] == 0 then
		call error("Double free: object of type DelayNode")
	else
		set DelayNode_firstFree = DelayNode_firstFree + 1
		set DelayNode_typeId[obj] = 0
	endif
endfunction

function destroyDelayNode takes integer this_2 returns nothing
	call DelayNode_onDestroy(this_2)
	call dealloc_DelayNode(this_2)
endfunction

function dispatch_DelayNode_destroyDelayNode takes integer this_2 returns nothing
	if DelayNode_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling DelayNode.DelayNode")
		else
			call error("Called DelayNode.DelayNode on invalid object.")
		endif
	endif
	call destroyDelayNode(this_2)
endfunction

function timer_getElapsed takes timer this_2 returns real
	return TimerGetElapsed(this_2)
endfunction

function getElapsedGameTime takes nothing returns real
	return timer_getElapsed(GameTimer_gameTimer)
endfunction

function timer_start takes timer this_2, real time, code timerCallBack returns nothing
	call TimerStart(this_2, time, false, timerCallBack)
endfunction

function DelayNode_recycle takes nothing returns nothing
	local integer tmp
	call DummyRecycler_recycle(DelayNode_u[DelayNode_first])
	set tmp = DelayNode_first
	if DelayNode_next[DelayNode_first] == 0 then
		set DelayNode_first = 0
	else
		set DelayNode_first = DelayNode_next[DelayNode_first]
		call timer_start(DelayNode_t, DelayNode_delayTime[DelayNode_first] - getElapsedGameTime(), ref_function_DelayNode_recycle)
	endif
	call dispatch_DelayNode_destroyDelayNode(tmp)
endfunction

function DynamicSound_TimedLoop_stopTimedLoopAndDestroy takes integer this_2 returns nothing
	set DynamicSound_TimedLoop_mode[this_2] = 3
endfunction

function dispatch_DynamicSound_SoundUtils_DynamicSound_TimedLoop_stopTimedLoopAndDestroy takes integer this_2 returns nothing
	if DynamicSound_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling DynamicSound.stopTimedLoopAndDestroy")
		else
			call error("Called DynamicSound.stopTimedLoopAndDestroy on invalid object.")
		endif
	endif
	call DynamicSound_TimedLoop_stopTimedLoopAndDestroy(this_2)
endfunction

function sound_isPlaying takes sound this_2 returns boolean
	return GetSoundIsPlaying(this_2)
endfunction

function sound_setPitch takes sound this_2, real pitch returns nothing
	call SetSoundPitch(this_2, pitch)
endfunction

function DynamicSound_setAbsolutePitch takes integer this_2, real pitch returns nothing
	if DynamicSound_soundData[this_2] == 0 then
		call error("Must play sound before setting pitch")
	endif
	if  not sound_isPlaying(SoundInstance_snd[DynamicSound_soundData[this_2]]) then
		if pitch == 1. then
			call sound_setPitch(SoundInstance_snd[DynamicSound_soundData[this_2]], 1.0001)
		else
			call sound_setPitch(SoundInstance_snd[DynamicSound_soundData[this_2]], pitch)
		endif
	else
		call sound_setPitch(SoundInstance_snd[DynamicSound_soundData[this_2]], 1. / DynamicSound_currentPitch[this_2])
		call sound_setPitch(SoundInstance_snd[DynamicSound_soundData[this_2]], pitch)
	endif
	set DynamicSound_currentPitch[this_2] = pitch
endfunction

function dispatch_DynamicSound_SoundUtils_DynamicSound_setAbsolutePitch takes integer this_2, real pitch returns nothing
	if DynamicSound_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling DynamicSound.setAbsolutePitch")
		else
			call error("Called DynamicSound.setAbsolutePitch on invalid object.")
		endif
	endif
	call DynamicSound_setAbsolutePitch(this_2, pitch)
endfunction

function real_lerp takes real this_2, real target, real alpha returns real
	return this_2 * (1.0 - alpha) + target * alpha
endfunction

function DynamicSound_onTimedLoop takes integer this_2 returns nothing
	if DynamicSound_soundData[this_2] != 0 then
		call dispatch_DynamicSound_SoundUtils_DynamicSound_setAbsolutePitch(this_2, real_lerp(DynamicSound_currentPitch[this_2], DynamicSound_targetPitch[this_2], DynamicSound_smoothness[this_2]))
		set DynamicSound_minimumLength[this_2] = DynamicSound_minimumLength[this_2] - Basics_ANIMATION_PERIOD
		if ( not sound_isPlaying(SoundInstance_snd[DynamicSound_soundData[this_2]])) and DynamicSound_minimumLength[this_2] <= 0. then
			call dispatch_DynamicSound_SoundUtils_DynamicSound_TimedLoop_stopTimedLoopAndDestroy(this_2)
		endif
	endif
endfunction

function dispatch_DynamicSound_SoundUtils_DynamicSound_onTimedLoop takes integer this_2 returns nothing
	if DynamicSound_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling DynamicSound.onTimedLoop")
		else
			call error("Called DynamicSound.onTimedLoop on invalid object.")
		endif
	endif
	call DynamicSound_onTimedLoop(this_2)
endfunction

function Log_warn takes string msg returns nothing
	call printLog(Player_localPlayer, 3, msg)
endfunction

function DynamicSound_TimedLoop_stopTimedLoop takes integer this_2 returns nothing
	set DynamicSound_TimedLoop_mode[this_2] = 2
endfunction

function dispatch_DynamicSound_SoundUtils_DynamicSound_TimedLoop_stopTimedLoop takes integer this_2 returns nothing
	if DynamicSound_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling DynamicSound.stopTimedLoop")
		else
			call error("Called DynamicSound.stopTimedLoop on invalid object.")
		endif
	endif
	call DynamicSound_TimedLoop_stopTimedLoop(this_2)
endfunction

function sound_stop takes sound this_2, boolean killWhenDone, boolean fadeOut returns nothing
	call StopSound(this_2, killWhenDone, fadeOut)
endfunction

function DynamicSound_onDestroy takes integer this_2 returns nothing
	call dispatch_DynamicSound_SoundUtils_DynamicSound_setAbsolutePitch(this_2, 1.)
	call sound_stop(SoundInstance_snd[DynamicSound_soundData[this_2]], false, false)
	call dispatch_DynamicSound_SoundUtils_DynamicSound_setAbsolutePitch(this_2, 1.)
	if DynamicSound_TimedLoop_mode[this_2] != 1 then
		call dispatch_DynamicSound_SoundUtils_DynamicSound_TimedLoop_stopTimedLoop(this_2)
		call Log_warn("Destroyed Instance using TimedLoop before stopping the Loop")
	endif
endfunction

function dealloc_DynamicSound takes integer obj returns nothing
	if DynamicSound_typeId[obj] == 0 then
		call error("Double free: object of type DynamicSound")
	else
		set DynamicSound_firstFree = DynamicSound_firstFree + 1
		set DynamicSound_typeId[obj] = 0
	endif
endfunction

function destroyDynamicSound takes integer this_2 returns nothing
	call DynamicSound_onDestroy(this_2)
	call dealloc_DynamicSound(this_2)
endfunction

function dispatch_DynamicSound_destroyDynamicSound takes integer this_2 returns nothing
	if DynamicSound_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling DynamicSound.DynamicSound")
		else
			call error("Called DynamicSound.DynamicSound on invalid object.")
		endif
	endif
	call destroyDynamicSound(this_2)
endfunction

function DynamicSound_TimedLoop_onExpire takes nothing returns boolean
	local integer i_2 = DynamicSound_TimedLoop_instanceCount - 1
	local integer t
	loop
		exitwhen  not (i_2 >= 0)
		set t = DynamicSound_TimedLoop_instances[i_2]
		if DynamicSound_TimedLoop_mode[t] != 0 then
			set DynamicSound_TimedLoop_instanceCount = DynamicSound_TimedLoop_instanceCount - 1
			set DynamicSound_TimedLoop_instances[i_2] = DynamicSound_TimedLoop_instances[DynamicSound_TimedLoop_instanceCount]
			if DynamicSound_TimedLoop_mode[t] == 3 then
				set DynamicSound_TimedLoop_mode[t] = 1
				call dispatch_DynamicSound_destroyDynamicSound(t)
			else
				set DynamicSound_TimedLoop_mode[t] = 1
			endif
		else
			call dispatch_DynamicSound_SoundUtils_DynamicSound_onTimedLoop(t)
			set i_2 = i_2 - 1
		endif
	endloop
	if DynamicSound_TimedLoop_instanceCount == 0 then
		call TriggerRemoveCondition(TimerUtils_timedLoopTrig, DynamicSound_TimedLoop_triggerCond)
		set DynamicSound_TimedLoop_triggerCond = null
		set TimerUtils_conditionCount = TimerUtils_conditionCount - 1
	endif
	return false
endfunction

function DamageInstance_onDestroy takes integer this_2 returns nothing
	set DamageInstance_count = DamageInstance_count - 1
endfunction

function dealloc_DamageInstance takes integer obj returns nothing
	if DamageInstance_typeId[obj] == 0 then
		call error("Double free: object of type DamageInstance")
	else
		set DamageInstance_nextFree[DamageInstance_firstFree] = obj
		set DamageInstance_firstFree = DamageInstance_firstFree + 1
		set DamageInstance_typeId[obj] = 0
	endif
endfunction

function destroyDamageInstance takes integer this_2 returns nothing
	call DamageInstance_onDestroy(this_2)
	call dealloc_DamageInstance(this_2)
endfunction

function dispatch_DamageInstance_destroyDamageInstance takes integer this_2 returns nothing
	if DamageInstance_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling DamageInstance.DamageInstance")
		else
			call error("Called DamageInstance.DamageInstance on invalid object.")
		endif
	endif
	call destroyDamageInstance(this_2)
endfunction

function dispatch_DamageListener_DamageEvent_DamageListener_onEvent takes integer this_2 returns nothing
endfunction

function alloc_DamageInstance takes nothing returns integer
	local integer this_2
	if DamageInstance_firstFree == 0 then
		if DamageInstance_maxIndex < 32768 then
			set DamageInstance_maxIndex = DamageInstance_maxIndex + 1
			set this_2 = DamageInstance_maxIndex
			set DamageInstance_typeId[this_2] = 668
		else
			call error("Out of memory: Could not create DamageInstance.")
			set this_2 = 0
		endif
	else
		set DamageInstance_firstFree = DamageInstance_firstFree - 1
		set this_2 = DamageInstance_nextFree[DamageInstance_firstFree]
		set DamageInstance_typeId[this_2] = 668
	endif
	return this_2
endfunction

function construct_DamageInstance takes integer this_2, integer id_2, unit source, unit target, real amount, integer damageType, integer damageElement returns nothing
	set DamageInstance_amount[this_2] = amount
	set DamageInstance_count = DamageInstance_count + 1
	set DamageInstance_stack[DamageInstance_count] = this_2
endfunction

function new_DamageInstance takes integer id_2, unit source, unit target, real amount, integer damageType, integer damageElement returns integer
	local integer this_2 = alloc_DamageInstance()
	call construct_DamageInstance(this_2, id_2, source, target, amount, damageType, damageElement)
	return this_2
endfunction

function DamageEvent_onDamage takes nothing returns nothing
	local real amount = GetEventDamage()
	local integer dmg
	local integer i_2
	local integer temp
	local integer listener
	if amount == 0. then
		return
	endif
	if DamageEvent_nextDamageType == 4 then
		if DamageEvent_DETECT_NATIVE_ABILITIES then
			if amount < 0. then
				set DamageEvent_nextDamageType = 1
				set amount = amount * -1.
			else
				set DamageEvent_nextDamageType = 0
				set DamageEvent_nextDamageElement = DamageEvent_DAMAGE_ELEMENT_ATTACK
			endif
		else
			set DamageEvent_nextDamageType = 0
			set DamageEvent_nextDamageElement = DamageEvent_DAMAGE_ELEMENT_ATTACK
		endif
	endif
	set dmg = new_DamageInstance(DamageEvent_nextDamageId, GetEventDamageSource(), GetTriggerUnit(), amount, DamageEvent_nextDamageType, DamageEvent_nextDamageElement)
	set DamageEvent_nextDamageId = 0
	set DamageEvent_nextDamageType = 4
	set DamageEvent_nextDamageElement = 0
	set i_2 = 0
	set temp = DamageEvent_maxPriority
	loop
		exitwhen i_2 > temp
		set listener = DamageEvent_firstListeners[i_2]
		loop
			exitwhen  not (listener != 0)
			call dispatch_DamageListener_DamageEvent_DamageListener_onEvent(listener)
			if DamageEvent_abort then
				set DamageInstance_amount[dmg] = 0.
				exitwhen true
			else
				set listener = DamageListener_next[listener]
			endif
		endloop
		if DamageEvent_abort then
			exitwhen true
		endif
		set i_2 = i_2 + 1
	endloop
	call BlzSetEventDamage(DamageInstance_amount[dmg])
	call dispatch_DamageInstance_destroyDamageInstance(dmg)
	set DamageEvent_abort = false
endfunction

function onEvent_add_DamageEvent takes integer this_2 returns nothing
	call DamageEvent_onDamage()
endfunction

function unit_getUserData takes unit this_2 returns integer
	return GetUnitUserData(this_2)
endfunction

function unit_getIndex takes unit this_2 returns integer
	return unit_getUserData(this_2)
endfunction

function SimpleSynchronizer_getSynchronizer takes unit what returns integer
	return SimpleSynchronizer_reverseLookup[unit_getIndex(what)]
endfunction

function bitset_containsPow takes integer this_val, integer pow returns boolean
	return ModuloInteger(this_val, pow * 2) >= pow
endfunction

function int_pow takes integer this_2, integer x returns integer
	local integer result_2 = 1
	local integer i_2 = 1
	local integer temp = x
	loop
		exitwhen i_2 > temp
		set result_2 = result_2 * this_2
		set i_2 = i_2 + 1
	endloop
	return result_2
endfunction

function bitset_add takes integer this_val, integer v returns integer
	local integer pow = int_pow(2, v)
	local integer cond_result_1
	local integer tuple_temp
	local integer tuple_temp_2
	if  not bitset_containsPow(this_val, pow) then
		set tuple_temp = this_val + pow
		set cond_result_1 = tuple_temp
	else
		set tuple_temp_2 = this_val
		set cond_result_1 = tuple_temp_2
	endif
	set bitset_add_return_val = cond_result_1
	return bitset_add_return_val
endfunction

function SimpleSynchronizer_areAllPlayersSynced takes integer this_2 returns boolean
	return SimpleSynchronizer_syncedPlayers_val[this_2] == SimpleSynchronizer_allPlayers_val
endfunction

function dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_areAllPlayersSynced takes integer this_2 returns boolean
	local boolean SyncSimple_SimpleSynchronizer_areAllPlayersSynced_result
	if SimpleSynchronizer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling SimpleSynchronizer.areAllPlayersSynced")
		else
			call error("Called SimpleSynchronizer.areAllPlayersSynced on invalid object.")
		endif
	endif
	set SyncSimple_SimpleSynchronizer_areAllPlayersSynced_result = SimpleSynchronizer_areAllPlayersSynced(this_2)
	return SyncSimple_SimpleSynchronizer_areAllPlayersSynced_result
endfunction

function SimpleSynchronizer_LinkedListModule_remove takes integer this_2 returns nothing
	set SimpleSynchronizer_LinkedListModule_size = SimpleSynchronizer_LinkedListModule_size - 1
	if this_2 != SimpleSynchronizer_LinkedListModule_first then
		set SimpleSynchronizer_LinkedListModule_next[SimpleSynchronizer_LinkedListModule_prev[this_2]] = SimpleSynchronizer_LinkedListModule_next[this_2]
	else
		set SimpleSynchronizer_LinkedListModule_first = SimpleSynchronizer_LinkedListModule_next[this_2]
	endif
	if this_2 != SimpleSynchronizer_LinkedListModule_last then
		set SimpleSynchronizer_LinkedListModule_prev[SimpleSynchronizer_LinkedListModule_next[this_2]] = SimpleSynchronizer_LinkedListModule_prev[this_2]
	else
		set SimpleSynchronizer_LinkedListModule_last = SimpleSynchronizer_LinkedListModule_prev[this_2]
	endif
endfunction

function dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_LinkedListModule_remove takes integer this_2 returns nothing
	if SimpleSynchronizer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling SimpleSynchronizer.remove")
		else
			call error("Called SimpleSynchronizer.remove on invalid object.")
		endif
	endif
	call SimpleSynchronizer_LinkedListModule_remove(this_2)
endfunction

function dealloc_SynchronizationCallback takes integer obj returns nothing
	if SynchronizationCallback_typeId[obj] == 0 then
		call error("Double free: object of type SynchronizationCallback")
	else
		set SynchronizationCallback_nextFree[SynchronizationCallback_firstFree] = obj
		set SynchronizationCallback_firstFree = SynchronizationCallback_firstFree + 1
		set SynchronizationCallback_typeId[obj] = 0
	endif
endfunction

function destroySynchronizationCallback takes integer this_2 returns nothing
	call dealloc_SynchronizationCallback(this_2)
endfunction

function dispatch_SynchronizationCallback_destroySynchronizationCallback takes integer this_2 returns nothing
	if SynchronizationCallback_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling SynchronizationCallback.SynchronizationCallback")
		else
			call error("Called SynchronizationCallback.SynchronizationCallback on invalid object.")
		endif
	endif
	call destroySynchronizationCallback(this_2)
endfunction

function SimpleSynchronizer_onDestroy takes integer this_2 returns nothing
	set SimpleSynchronizer_reverseLookup[unit_getIndex(SimpleSynchronizer_dummy[this_2])] = 0
	call unit_remove(SimpleSynchronizer_dummy[this_2])
	call dispatch_SynchronizationCallback_destroySynchronizationCallback(SimpleSynchronizer_callback[this_2])
	call dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_LinkedListModule_remove(this_2)
endfunction

function dealloc_SimpleSynchronizer takes integer obj returns nothing
	if SimpleSynchronizer_typeId[obj] == 0 then
		call error("Double free: object of type SimpleSynchronizer")
	else
		set SimpleSynchronizer_nextFree[SimpleSynchronizer_firstFree] = obj
		set SimpleSynchronizer_firstFree = SimpleSynchronizer_firstFree + 1
		set SimpleSynchronizer_typeId[obj] = 0
	endif
endfunction

function destroySimpleSynchronizer takes integer this_2 returns nothing
	call SimpleSynchronizer_onDestroy(this_2)
	call dealloc_SimpleSynchronizer(this_2)
endfunction

function dispatch_SimpleSynchronizer_destroySimpleSynchronizer takes integer this_2 returns nothing
	if SimpleSynchronizer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling SimpleSynchronizer.SimpleSynchronizer")
		else
			call error("Called SimpleSynchronizer.SimpleSynchronizer on invalid object.")
		endif
	endif
	call destroySimpleSynchronizer(this_2)
endfunction

function alloc_ForForceCallback_execute_Network_Network takes nothing returns integer
	local integer this_2
	if ForForceCallback_firstFree == 0 then
		if ForForceCallback_maxIndex < 32768 then
			set ForForceCallback_maxIndex = ForForceCallback_maxIndex + 1
			set this_2 = ForForceCallback_maxIndex
			set ForForceCallback_typeId[this_2] = 700
		else
			call error("Out of memory: Could not create ForForceCallback_execute_Network_Network.")
			set this_2 = 0
		endif
	else
		set ForForceCallback_firstFree = ForForceCallback_firstFree - 1
		set this_2 = ForForceCallback_nextFree[ForForceCallback_firstFree]
		set ForForceCallback_typeId[this_2] = 700
	endif
	return this_2
endfunction

function alloc_LimitedExecuteAction_executeWhile_Network_Network_519 takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 764
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 764
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_Network_Network_536 takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 784
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 784
	endif
	return this_2
endfunction

function AbstractBuffer_setMode takes integer this_2, integer newMode returns nothing
	set AbstractBuffer_mode[this_2] = newMode
endfunction

function dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_setMode takes integer this_2, integer newMode returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractBuffer.setMode")
		else
			call error("Called AbstractBuffer.setMode on invalid object.")
		endif
	endif
	call AbstractBuffer_setMode(this_2, newMode)
endfunction

function HashBuffer_resetRead takes integer this_2 returns nothing
	set HashBuffer_integerReadIndex[this_2] = -1
	set HashBuffer_realReadIndex[this_2] = -1
	set HashBuffer_booleanReadIndex[this_2] = -1
	set HashBuffer_stringReadIndex[this_2] = -1
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_resetRead takes integer this_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.resetRead")
		else
			call error("Called HashBuffer.resetRead on invalid object.")
		endif
	endif
	call HashBuffer_resetRead(this_2)
endfunction

function onFinish_Network_Network takes integer this_2, integer w_result, integer w_buffer returns nothing
	call error("Network: did not specify any callback function")
endfunction

function alloc_ForForceCallback_try_start_onSynced_PersistentStore_Persistable takes nothing returns integer
	local integer this_2
	if ForForceCallback_firstFree == 0 then
		if ForForceCallback_maxIndex < 32768 then
			set ForForceCallback_maxIndex = ForForceCallback_maxIndex + 1
			set this_2 = ForForceCallback_maxIndex
			set ForForceCallback_typeId[this_2] = 706
		else
			call error("Out of memory: Could not create ForForceCallback_try_start_onSynced_PersistentStore_Persistable.")
			set this_2 = 0
		endif
	else
		set ForForceCallback_firstFree = ForForceCallback_firstFree - 1
		set this_2 = ForForceCallback_nextFree[ForForceCallback_firstFree]
		set ForForceCallback_typeId[this_2] = 706
	endif
	return this_2
endfunction

function BufferMode_toString takes integer this_2 returns string
	local integer temp = this_2
	local string value_2
	if temp == 0 then
		set value_2 = "READ"
	elseif temp == 1 then
		set value_2 = "WRITE"
	elseif temp == 2 then
		set value_2 = "READ_WRITE"
	else
		set value_2 = "LOCKED"
	endif
	return value_2
endfunction

function AbstractBuffer_isValid takes integer this_2 returns boolean
	return AbstractBuffer_failureMode[this_2] == 0
endfunction

function dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_isValid takes integer this_2 returns boolean
	local boolean BufferInterface_AbstractBuffer_isValid_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractBuffer.isValid")
		else
			call error("Called AbstractBuffer.isValid on invalid object.")
		endif
	endif
	set BufferInterface_AbstractBuffer_isValid_result = AbstractBuffer_isValid(this_2)
	return BufferInterface_AbstractBuffer_isValid_result
endfunction

function AbstractBuffer_checkFailed takes integer this_2 returns nothing
	if SafetyChecks_SAFETY_CHECKS_ENABLED and ( not dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_isValid(this_2)) then
		call error("Buffer: trying to use a failed buffer")
	endif
endfunction

function dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkFailed takes integer this_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractBuffer.checkFailed")
		else
			call error("Called AbstractBuffer.checkFailed on invalid object.")
		endif
	endif
	call AbstractBuffer_checkFailed(this_2)
endfunction

function AbstractBuffer_isReadable takes integer this_2 returns boolean
	return AbstractBuffer_mode[this_2] == 0 or AbstractBuffer_mode[this_2] == 2
endfunction

function dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_isReadable takes integer this_2 returns boolean
	local boolean BufferInterface_AbstractBuffer_isReadable_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractBuffer.isReadable")
		else
			call error("Called AbstractBuffer.isReadable on invalid object.")
		endif
	endif
	set BufferInterface_AbstractBuffer_isReadable_result = AbstractBuffer_isReadable(this_2)
	return BufferInterface_AbstractBuffer_isReadable_result
endfunction

function AbstractBuffer_checkRead takes integer this_2 returns nothing
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkFailed(this_2)
	if SafetyChecks_SAFETY_CHECKS_ENABLED and ( not dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_isReadable(this_2)) then
		call error("Buffer: cannot read from buffer with mode " + BufferMode_toString(AbstractBuffer_mode[this_2]))
	endif
endfunction

function dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkRead takes integer this_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractBuffer.checkRead")
		else
			call error("Called AbstractBuffer.checkRead on invalid object.")
		endif
	endif
	call AbstractBuffer_checkRead(this_2)
endfunction

function Table_hasBoolean takes integer this_2, integer parentKey returns boolean
	return hashtable_hasBoolean(Table_ht, this_2, parentKey)
endfunction

function dispatch_Table_Table_Table_hasBoolean takes integer this_2, integer parentKey returns boolean
	local boolean Table_Table_hasBoolean_result
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.hasBoolean")
		else
			call error("Called Table.hasBoolean on invalid object.")
		endif
	endif
	set Table_Table_hasBoolean_result = Table_hasBoolean(this_2, parentKey)
	return Table_Table_hasBoolean_result
endfunction

function Table_loadBoolean takes integer this_2, integer parentKey returns boolean
	return hashtable_loadBoolean(Table_ht, this_2, parentKey)
endfunction

function dispatch_Table_Table_Table_loadBoolean takes integer this_2, integer parentKey returns boolean
	local boolean Table_Table_loadBoolean_result
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.loadBoolean")
		else
			call error("Called Table.loadBoolean on invalid object.")
		endif
	endif
	set Table_Table_loadBoolean_result = Table_loadBoolean(this_2, parentKey)
	return Table_Table_loadBoolean_result
endfunction

function int_toString takes integer this_2 returns string
	return I2S(this_2)
endfunction

function HashBuffer_readBoolean takes integer this_2 returns boolean
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkRead(this_2)
	set HashBuffer_booleanReadIndex[this_2] = HashBuffer_booleanReadIndex[this_2] + 1
	if  not dispatch_Table_Table_Table_hasBoolean(HashBuffer_buffer[this_2], HashBuffer_booleanReadIndex[this_2]) then
		call error("HashBuffer: trying to read non-present boolean at pos#" + int_toString(HashBuffer_booleanReadIndex[this_2]))
	endif
	return dispatch_Table_Table_Table_loadBoolean(HashBuffer_buffer[this_2], HashBuffer_booleanReadIndex[this_2])
endfunction

function AbstractBuffer_fail takes integer this_2, integer mode, string message returns nothing
	set AbstractBuffer_failureMode[this_2] = mode
	if mode == 2 then
		call error("Buffer: " + message)
	else
		call Log_warn("Buffer: " + message)
	endif
endfunction

function dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_fail takes integer this_2, integer mode, string message returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractBuffer.fail")
		else
			call error("Called AbstractBuffer.fail on invalid object.")
		endif
	endif
	call AbstractBuffer_fail(this_2, mode, message)
endfunction

function LinkedList_size_2 takes integer this_2 returns integer
	return LinkedList_size[this_2]
endfunction

function dispatch_LinkedList_LinkedList_LinkedList_size takes integer this_2 returns integer
	local integer LinkedList_LinkedList_size_result
	if LinkedList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LinkedList.size")
		else
			call error("Called LinkedList.size on invalid object.")
		endif
	endif
	set LinkedList_LinkedList_size_result = LinkedList_size_2(this_2)
	return LinkedList_LinkedList_size_result
endfunction

function string_length takes string this_2 returns integer
	return StringLength(this_2)
endfunction

function AbstractStringBuffer_isDataAvailable takes integer this_2 returns boolean
	return dispatch_LinkedList_LinkedList_LinkedList_size(AbstractStringBuffer_chunks[this_2]) > 0 or string_length(AbstractStringBuffer_readBuffer[this_2]) > 0
endfunction

function dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_isDataAvailable takes integer this_2 returns boolean
	local boolean StringBuffer_AbstractStringBuffer_isDataAvailable_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractStringBuffer.isDataAvailable")
		else
			call error("Called AbstractStringBuffer.isDataAvailable on invalid object.")
		endif
	endif
	set StringBuffer_AbstractStringBuffer_isDataAvailable_result = AbstractStringBuffer_isDataAvailable(this_2)
	return StringBuffer_AbstractStringBuffer_isDataAvailable_result
endfunction

function AbstractStringBuffer_checkDataAvailable takes integer this_2 returns nothing
	if  not dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_isDataAvailable(this_2) then
		call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_fail(this_2, 1, "reached EOF")
	endif
endfunction

function dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_checkDataAvailable takes integer this_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractStringBuffer.checkDataAvailable")
		else
			call error("Called AbstractStringBuffer.checkDataAvailable on invalid object.")
		endif
	endif
	call AbstractStringBuffer_checkDataAvailable(this_2)
endfunction

function ChunkElement_onDestroy takes integer this_2 returns nothing
endfunction

function dealloc_ChunkElement takes integer obj returns nothing
	if ChunkElement_typeId[obj] == 0 then
		call error("Double free: object of type ChunkElement")
	else
		set ChunkElement_nextFree[ChunkElement_firstFree] = obj
		set ChunkElement_firstFree = ChunkElement_firstFree + 1
		set ChunkElement_typeId[obj] = 0
	endif
endfunction

function destroyChunkElement takes integer this_2 returns nothing
	call ChunkElement_onDestroy(this_2)
	call dealloc_ChunkElement(this_2)
endfunction

function dispatch_ChunkElement_destroyChunkElement takes integer this_2 returns nothing
	if ChunkElement_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling ChunkElement.ChunkElement")
		else
			call error("Called ChunkElement.ChunkElement on invalid object.")
		endif
	endif
	call destroyChunkElement(this_2)
endfunction

function LLEntry_onDestroy takes integer this_2 returns nothing
endfunction

function dealloc_LLEntry takes integer obj returns nothing
	if LLEntry_typeId[obj] == 0 then
		call error("Double free: object of type LLEntry")
	else
		set LLEntry_nextFree[LLEntry_firstFree] = obj
		set LLEntry_firstFree = LLEntry_firstFree + 1
		set LLEntry_typeId[obj] = 0
	endif
endfunction

function destroyLLEntry takes integer this_2 returns nothing
	call LLEntry_onDestroy(this_2)
	call dealloc_LLEntry(this_2)
endfunction

function dispatch_LLEntry_destroyLLEntry takes integer this_2 returns nothing
	if LLEntry_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LLEntry.LLEntry")
		else
			call error("Called LLEntry.LLEntry on invalid object.")
		endif
	endif
	call destroyLLEntry(this_2)
endfunction

function LinkedList_removeEntry takes integer this_2, integer entry returns nothing
	set LLEntry_next[LLEntry_prev[entry]] = LLEntry_next[entry]
	set LLEntry_prev[LLEntry_next[entry]] = LLEntry_prev[entry]
	call dispatch_LLEntry_destroyLLEntry(entry)
	set LinkedList_size[this_2] = LinkedList_size[this_2] - 1
endfunction

function dispatch_LinkedList_LinkedList_LinkedList_removeEntry takes integer this_2, integer entry returns nothing
	if LinkedList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LinkedList.removeEntry")
		else
			call error("Called LinkedList.removeEntry on invalid object.")
		endif
	endif
	call LinkedList_removeEntry(this_2, entry)
endfunction

function LinkedList_dequeue takes integer this_2 returns integer
	local integer top = LLEntry_next[LinkedList_dummy[this_2]]
	local integer result_2 = 0
	if top != LinkedList_dummy[this_2] then
		set result_2 = LLEntry_elem[top]
		call dispatch_LinkedList_LinkedList_LinkedList_removeEntry(this_2, top)
	endif
	return result_2
endfunction

function dispatch_LinkedList_LinkedList_LinkedList_dequeue takes integer this_2 returns integer
	local integer LinkedList_LinkedList_dequeue_result
	if LinkedList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LinkedList.dequeue")
		else
			call error("Called LinkedList.dequeue on invalid object.")
		endif
	endif
	set LinkedList_LinkedList_dequeue_result = LinkedList_dequeue(this_2)
	return LinkedList_LinkedList_dequeue_result
endfunction

function LinkedList_isEmpty takes integer this_2 returns boolean
	return LinkedList_size[this_2] == 0
endfunction

function dispatch_LinkedList_LinkedList_LinkedList_isEmpty takes integer this_2 returns boolean
	local boolean LinkedList_LinkedList_isEmpty_result
	if LinkedList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LinkedList.isEmpty")
		else
			call error("Called LinkedList.isEmpty on invalid object.")
		endif
	endif
	set LinkedList_LinkedList_isEmpty_result = LinkedList_isEmpty(this_2)
	return LinkedList_LinkedList_isEmpty_result
endfunction

function AbstractStringBuffer_nextChunk takes integer this_2 returns nothing
	local integer chunk
	if dispatch_LinkedList_LinkedList_LinkedList_isEmpty(AbstractStringBuffer_chunks[this_2]) then
		call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_fail(this_2, 1, "reached EOF")
		return
	endif
	set chunk = dispatch_LinkedList_LinkedList_LinkedList_dequeue(AbstractStringBuffer_chunks[this_2])
	set AbstractStringBuffer_readBuffer[this_2] = AbstractStringBuffer_readBuffer[this_2] + ChunkElement_content[chunk]
	call dispatch_ChunkElement_destroyChunkElement(chunk)
endfunction

function dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_nextChunk takes integer this_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractStringBuffer.nextChunk")
		else
			call error("Called AbstractStringBuffer.nextChunk on invalid object.")
		endif
	endif
	call AbstractStringBuffer_nextChunk(this_2)
endfunction

function string_substring takes string this_2, integer start, integer stop returns string
	return SubString(this_2, start, stop)
endfunction

function string_substring_1308 takes string this_2, integer start returns string
	return SubString(this_2, start, string_length(this_2))
endfunction

function AbstractStringBuffer_popString takes integer this_2, integer length_2 returns string
	local string value_2
	if SafetyChecks_SAFETY_CHECKS_ENABLED and length_2 > AbstractStringBuffer_MAX_BUFFER_SIZE then
		call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_fail(this_2, 2, "trying to pop a string above max size")
	endif
	if length_2 == 0 then
		return ""
	endif
	call dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_checkDataAvailable(this_2)
	loop
		exitwhen  not (dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_isValid(this_2) and string_length(AbstractStringBuffer_readBuffer[this_2]) < length_2)
		call dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_nextChunk(this_2)
	endloop
	if dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_isValid(this_2) then
		set value_2 = string_substring(AbstractStringBuffer_readBuffer[this_2], 0, length_2)
		set AbstractStringBuffer_readBuffer[this_2] = string_substring_1308(AbstractStringBuffer_readBuffer[this_2], length_2)
		return value_2
	else
		return null
	endif
endfunction

function dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_popString takes integer this_2, integer length_2 returns string
	local string StringBuffer_AbstractStringBuffer_popString_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractStringBuffer.popString")
		else
			call error("Called AbstractStringBuffer.popString on invalid object.")
		endif
	endif
	set StringBuffer_AbstractStringBuffer_popString_result = AbstractStringBuffer_popString(this_2, length_2)
	return StringBuffer_AbstractStringBuffer_popString_result
endfunction

function ValueType_toString takes integer this_2 returns string
	local string id_2 = ""
	local integer temp = this_2
	if temp == 0 then
		set id_2 = "i"
	elseif temp == 1 then
		set id_2 = "r"
	elseif temp == 3 then
		set id_2 = "b"
	elseif temp == 2 then
		set id_2 = "s"
	else
		call error("ValueType: INVALID should never be serializable")
	endif
	return id_2
endfunction

function valueTypeFromString takes string value_2 returns integer
	local integer valueType = 4
	local string temp = value_2
	if temp == "i" then
		set valueType = 0
	elseif temp == "r" then
		set valueType = 1
	elseif temp == "b" then
		set valueType = 3
	elseif temp == "s" then
		set valueType = 2
	endif
	return valueType
endfunction

function OrderedStringBuffer_popTypeIdentifier takes integer this_2 returns integer
	local integer valueType = valueTypeFromString(dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_popString(this_2, 1))
	if valueType == 4 then
		call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_fail(this_2, 3, "malformed input")
	endif
	return valueType
endfunction

function dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_popTypeIdentifier takes integer this_2 returns integer
	local integer OrderedStringBuffer_OrderedStringBuffer_popTypeIdentifier_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling OrderedStringBuffer.popTypeIdentifier")
		else
			call error("Called OrderedStringBuffer.popTypeIdentifier on invalid object.")
		endif
	endif
	set OrderedStringBuffer_OrderedStringBuffer_popTypeIdentifier_result = OrderedStringBuffer_popTypeIdentifier(this_2)
	return OrderedStringBuffer_OrderedStringBuffer_popTypeIdentifier_result
endfunction

function OrderedStringBuffer_checkType takes integer this_2, integer toCheck returns boolean
	local integer currentType = dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_popTypeIdentifier(this_2)
	if  not dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_isValid(this_2) then
		return false
	endif
	if currentType != toCheck then
		call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_fail(this_2, 4, "tried to read " + ValueType_toString(toCheck) + " but was " + ValueType_toString(currentType))
		return false
	endif
	return true
endfunction

function dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_checkType takes integer this_2, integer toCheck returns boolean
	local boolean OrderedStringBuffer_OrderedStringBuffer_checkType_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling OrderedStringBuffer.checkType")
		else
			call error("Called OrderedStringBuffer.checkType on invalid object.")
		endif
	endif
	set OrderedStringBuffer_OrderedStringBuffer_checkType_result = OrderedStringBuffer_checkType(this_2, toCheck)
	return OrderedStringBuffer_OrderedStringBuffer_checkType_result
endfunction

function OrderedStringBuffer_readBoolean takes integer this_2 returns boolean
	local boolean cond_result
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkRead(this_2)
	if dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_checkType(this_2, 3) then
		set cond_result = dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_popString(this_2, 1) == "1"
	else
		set cond_result = false
	endif
	return cond_result
endfunction

function dispatch_Buffer_BufferInterface_Buffer_readBoolean takes integer this_2 returns boolean
	local boolean BufferInterface_Buffer_readBoolean_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Buffer.readBoolean")
		else
			call error("Called Buffer.readBoolean on invalid object.")
		endif
	endif
	if Buffer_typeId[this_2] <= 644 then
		set BufferInterface_Buffer_readBoolean_result = OrderedStringBuffer_readBoolean(this_2)
	else
		set BufferInterface_Buffer_readBoolean_result = HashBuffer_readBoolean(this_2)
	endif
	return BufferInterface_Buffer_readBoolean_result
endfunction

function dispatch_PersistableLoadCallback_Persistable_PersistableLoadCallback_onLoaded takes integer this_2, integer status returns nothing
endfunction

function Persistable_onLoaded takes integer this_2, integer w_status returns nothing
endfunction

function dispatch_Persistable_Persistable_Persistable_onLoaded takes integer this_2, integer w_status returns nothing
	if BufferSerializable_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Persistable.onLoaded")
		else
			call error("Called Persistable.onLoaded on invalid object.")
		endif
	endif
	call Persistable_onLoaded(this_2, w_status)
endfunction

function dispatch_Persistable_Persistable_Persistable_supplyDefault takes integer this_2 returns nothing
endfunction

function PersistentStore_finishWithStatus takes integer this_2, integer status, integer callback_2 returns nothing
	if status != 0 then
		call dispatch_Persistable_Persistable_Persistable_supplyDefault(PersistentStore_entity[this_2])
	endif
	if callback_2 != 0 then
		call dispatch_PersistableLoadCallback_Persistable_PersistableLoadCallback_onLoaded(callback_2, status)
	endif
	call dispatch_Persistable_Persistable_Persistable_onLoaded(PersistentStore_entity[this_2], status)
endfunction

function dispatch_PersistentStore_Persistable_PersistentStore_finishWithStatus takes integer this_2, integer status, integer callback_2 returns nothing
	if PersistentStore_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling PersistentStore.finishWithStatus")
		else
			call error("Called PersistentStore.finishWithStatus on invalid object.")
		endif
	endif
	call PersistentStore_finishWithStatus(this_2, status, callback_2)
endfunction

function force_forEach takes force this_2, code callback_2 returns nothing
	call ForForce(this_2, callback_2)
endfunction

function isLastCallbackSuccessful takes nothing returns boolean
	return Execute_tempCallbacksSuccess[Execute_tempCallbacksCount]
endfunction

function dealloc_ForForceCallback takes integer obj returns nothing
	if ForForceCallback_typeId[obj] == 0 then
		call error("Double free: object of type ForForceCallback")
	else
		set ForForceCallback_nextFree[ForForceCallback_firstFree] = obj
		set ForForceCallback_firstFree = ForForceCallback_firstFree + 1
		set ForForceCallback_typeId[obj] = 0
	endif
endfunction

function destroyForForceCallback takes integer this_2 returns nothing
	call dealloc_ForForceCallback(this_2)
endfunction

function dispatch_ForForceCallback_destroyForForceCallback takes integer this_2 returns nothing
	if ForForceCallback_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling ForForceCallback.ForForceCallback")
		else
			call error("Called ForForceCallback.ForForceCallback on invalid object.")
		endif
	endif
	call destroyForForceCallback(this_2)
endfunction

function popCallback_1225 takes nothing returns nothing
	set Execute_tempCallbacksCount = Execute_tempCallbacksCount - 1
	call dispatch_ForForceCallback_destroyForForceCallback(Execute_tempCallbacks[Execute_tempCallbacksCount])
endfunction

function pushCallback_1234 takes integer c returns nothing
	set Execute_tempCallbacks[Execute_tempCallbacksCount] = c
	set Execute_tempCallbacksSuccess[Execute_tempCallbacksCount] = false
	set Execute_tempCallbacksCount = Execute_tempCallbacksCount + 1
endfunction

function try takes integer c returns boolean
	local boolean suppressErrors
	call pushCallback_1234(c)
	set suppressErrors = ErrorHandling_suppressErrorMessages
	set ErrorHandling_suppressErrorMessages = true
	call force_forEach(Execute_executeForce, ref_function_executeCurrentCallback)
	set ErrorHandling_suppressErrorMessages = suppressErrors
	call popCallback_1225()
	return isLastCallbackSuccessful()
endfunction

function onFinish_start_onSynced_PersistentStore_Persistable takes integer this_2, integer status, integer buffer_2 returns nothing
	local integer loadStatus = 0
	local boolean decodeSuccess
	local boolean deserializeSuccess
	local integer clVar
	if status == 1 then
		set loadStatus = 4
	else
		set decodeSuccess = dispatch_Buffer_BufferInterface_Buffer_readBoolean(buffer_2)
		if decodeSuccess then
			set clVar = alloc_ForForceCallback_try_start_onSynced_PersistentStore_Persistable()
			set this_798[clVar] = this_797[this_2]
			set buffer_615[clVar] = buffer_2
			set deserializeSuccess = try(clVar)
			if  not deserializeSuccess then
				set loadStatus = 3
			endif
		else
			set loadStatus = 2
		endif
	endif
	call dispatch_PersistentStore_Persistable_PersistentStore_finishWithStatus(this_797[this_2], loadStatus, callback_618[this_2])
endfunction

function dispatch_NetworkFinishedCallback_Network_NetworkFinishedCallback_onFinish takes integer this_2, integer status, integer buffer_2 returns nothing
	if NetworkFinishedCallback_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling NetworkFinishedCallback.onFinish")
		else
			call error("Called NetworkFinishedCallback.onFinish on invalid object.")
		endif
	endif
	if NetworkFinishedCallback_typeId[this_2] <= 807 then
		call onFinish_Network_Network(this_2, status, buffer_2)
	else
		call onFinish_start_onSynced_PersistentStore_Persistable(this_2, status, buffer_2)
	endif
endfunction

function GamecacheBuffer_onDestroy takes integer this_2 returns nothing
endfunction

function dealloc_GamecacheBuffer takes integer obj returns nothing
	if GamecacheBuffer_typeId[obj] == 0 then
		call error("Double free: object of type GamecacheBuffer")
	else
		set GamecacheBuffer_firstFree = GamecacheBuffer_firstFree + 1
		set GamecacheBuffer_typeId[obj] = 0
	endif
endfunction

function destroyGamecacheBuffer takes integer this_2 returns nothing
	call GamecacheBuffer_onDestroy(this_2)
	call dealloc_GamecacheBuffer(this_2)
endfunction

function dispatch_GamecacheBuffer_destroyGamecacheBuffer takes integer this_2 returns nothing
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.GamecacheBuffer")
		else
			call error("Called GamecacheBuffer.GamecacheBuffer on invalid object.")
		endif
	endif
	call destroyGamecacheBuffer(this_2)
endfunction

function AbstractBuffer_onDestroy takes integer this_2 returns nothing
endfunction

function hashtable_flushChild takes hashtable this_2, integer parentKey returns nothing
	call FlushChildHashtable(this_2, parentKey)
endfunction

function Table_flush takes integer this_2 returns nothing
	call hashtable_flushChild(Table_ht, this_2)
endfunction

function HashMap_flush takes integer this_2 returns nothing
	set HashMap_size[this_2] = 0
	call Table_flush(this_2)
endfunction

function HashList_clear takes integer this_2 returns nothing
	call hashtable_flushChild(HashList_ht, this_2)
	call hashtable_flushChild(HashList_occurences, this_2)
	set HashList_size[this_2] = 0
endfunction

function HashSet_clear takes integer this_2 returns nothing
	call HashList_clear(this_2)
	call hashtable_flushChild(HashSet_position, this_2)
endfunction

function dispatch_HashList_HashList_HashList_clear takes integer this_2 returns nothing
	if HashList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashList.clear")
		else
			call error("Called HashList.clear on invalid object.")
		endif
	endif
	if HashList_typeId[this_2] <= 716 then
		call HashList_clear(this_2)
	else
		call HashSet_clear(this_2)
	endif
endfunction

function IterableMap_flush takes integer this_2 returns nothing
	if  not IterableMap__destroyed[this_2] then
		call dispatch_HashList_HashList_HashList_clear(IterableMap_keys[this_2])
	endif
	call HashMap_flush(this_2)
endfunction

function dispatch_Table_Table_Table_flush takes integer this_2 returns nothing
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.flush")
		else
			call error("Called Table.flush on invalid object.")
		endif
	endif
	if Table_typeId[this_2] <= 856 then
		if Table_typeId[this_2] <= 855 then
			call Table_flush(this_2)
		else
			call HashMap_flush(this_2)
		endif
	else
		call IterableMap_flush(this_2)
	endif
endfunction

function Table_onDestroy takes integer this_2 returns nothing
	call dispatch_Table_Table_Table_flush(this_2)
endfunction

function HashMap_onDestroy takes integer this_2 returns nothing
	call Table_onDestroy(this_2)
endfunction

function HashList_onDestroy takes integer this_2 returns nothing
	call dispatch_HashList_HashList_HashList_clear(this_2)
endfunction

function dealloc_HashList takes integer obj returns nothing
	if HashList_typeId[obj] == 0 then
		call error("Double free: object of type HashList")
	else
		set HashList_firstFree = HashList_firstFree + 1
		set HashList_typeId[obj] = 0
	endif
endfunction

function destroyHashList takes integer this_2 returns nothing
	call HashList_onDestroy(this_2)
	call dealloc_HashList(this_2)
endfunction

function HashSet_onDestroy takes integer this_2 returns nothing
	call hashtable_flushChild(HashSet_position, this_2)
	call HashList_onDestroy(this_2)
endfunction

function dealloc_HashSet takes integer obj returns nothing
	if HashList_typeId[obj] == 0 then
		call error("Double free: object of type HashSet")
	else
		set HashList_firstFree = HashList_firstFree + 1
		set HashList_typeId[obj] = 0
	endif
endfunction

function destroyHashSet takes integer this_2 returns nothing
	call HashSet_onDestroy(this_2)
	call dealloc_HashSet(this_2)
endfunction

function dispatch_HashList_destroyHashList takes integer this_2 returns nothing
	if HashList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashList.HashList")
		else
			call error("Called HashList.HashList on invalid object.")
		endif
	endif
	if HashList_typeId[this_2] <= 716 then
		call destroyHashList(this_2)
	else
		call destroyHashSet(this_2)
	endif
endfunction

function IterableMap_onDestroy takes integer this_2 returns nothing
	call dispatch_HashList_destroyHashList(IterableMap_keys[this_2])
	set IterableMap__destroyed[this_2] = true
	call HashMap_onDestroy(this_2)
endfunction

function dealloc_IterableMap takes integer obj returns nothing
	if Table_typeId[obj] == 0 then
		call error("Double free: object of type IterableMap")
	else
		set Table_nextFree[Table_firstFree] = obj
		set Table_firstFree = Table_firstFree + 1
		set Table_typeId[obj] = 0
	endif
endfunction

function destroyIterableMap takes integer this_2 returns nothing
	call IterableMap_onDestroy(this_2)
	call dealloc_IterableMap(this_2)
endfunction

function dealloc_Table takes integer obj returns nothing
	if Table_typeId[obj] == 0 then
		call error("Double free: object of type Table")
	else
		set Table_nextFree[Table_firstFree] = obj
		set Table_firstFree = Table_firstFree + 1
		set Table_typeId[obj] = 0
	endif
endfunction

function destroyTable takes integer this_2 returns nothing
	call Table_onDestroy(this_2)
	call dealloc_Table(this_2)
endfunction

function dispatch_Table_destroyTable takes integer this_2 returns nothing
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.Table")
		else
			call error("Called Table.Table on invalid object.")
		endif
	endif
	if Table_typeId[this_2] <= 856 then
		call destroyTable(this_2)
	else
		call destroyIterableMap(this_2)
	endif
endfunction

function HashBuffer_onDestroy takes integer this_2 returns nothing
	call dispatch_Table_destroyTable(HashBuffer_buffer[this_2])
	call AbstractBuffer_onDestroy(this_2)
endfunction

function dealloc_HashBuffer takes integer obj returns nothing
	if Buffer_typeId[obj] == 0 then
		call error("Double free: object of type HashBuffer")
	else
		set Buffer_nextFree[Buffer_firstFree] = obj
		set Buffer_firstFree = Buffer_firstFree + 1
		set Buffer_typeId[obj] = 0
	endif
endfunction

function destroyHashBuffer takes integer this_2 returns nothing
	call HashBuffer_onDestroy(this_2)
	call dealloc_HashBuffer(this_2)
endfunction

function dispatch_HashBuffer_destroyHashBuffer takes integer this_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.HashBuffer")
		else
			call error("Called HashBuffer.HashBuffer on invalid object.")
		endif
	endif
	call destroyHashBuffer(this_2)
endfunction

function MetadataStore_onDestroy takes integer this_2 returns nothing
endfunction

function dealloc_MetadataStore takes integer obj returns nothing
	if MetadataStore_typeId[obj] == 0 then
		call error("Double free: object of type MetadataStore")
	else
		set MetadataStore_firstFree = MetadataStore_firstFree + 1
		set MetadataStore_typeId[obj] = 0
	endif
endfunction

function destroyMetadataStore takes integer this_2 returns nothing
	call MetadataStore_onDestroy(this_2)
	call dealloc_MetadataStore(this_2)
endfunction

function dispatch_MetadataStore_destroyMetadataStore takes integer this_2 returns nothing
	if MetadataStore_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling MetadataStore.MetadataStore")
		else
			call error("Called MetadataStore.MetadataStore on invalid object.")
		endif
	endif
	call destroyMetadataStore(this_2)
endfunction

function dealloc_NetworkFinishedCallback takes integer obj returns nothing
	if NetworkFinishedCallback_typeId[obj] == 0 then
		call error("Double free: object of type NetworkFinishedCallback")
	else
		set NetworkFinishedCallback_nextFree[NetworkFinishedCallback_firstFree] = obj
		set NetworkFinishedCallback_firstFree = NetworkFinishedCallback_firstFree + 1
		set NetworkFinishedCallback_typeId[obj] = 0
	endif
endfunction

function destroyNetworkFinishedCallback takes integer this_2 returns nothing
	call dealloc_NetworkFinishedCallback(this_2)
endfunction

function dispatch_NetworkFinishedCallback_destroyNetworkFinishedCallback takes integer this_2 returns nothing
	if NetworkFinishedCallback_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling NetworkFinishedCallback.NetworkFinishedCallback")
		else
			call error("Called NetworkFinishedCallback.NetworkFinishedCallback on invalid object.")
		endif
	endif
	call destroyNetworkFinishedCallback(this_2)
endfunction

function StringEncoder_onDestroy takes integer this_2 returns nothing
	call dispatch_HashBuffer_destroyHashBuffer(StringEncoder_buffer[this_2])
endfunction

function dealloc_StringEncoder takes integer obj returns nothing
	if StringEncoder_typeId[obj] == 0 then
		call error("Double free: object of type StringEncoder")
	else
		set StringEncoder_firstFree = StringEncoder_firstFree + 1
		set StringEncoder_typeId[obj] = 0
	endif
endfunction

function destroyStringEncoder takes integer this_2 returns nothing
	call StringEncoder_onDestroy(this_2)
	call dealloc_StringEncoder(this_2)
endfunction

function dispatch_StringEncoder_destroyStringEncoder takes integer this_2 returns nothing
	if StringEncoder_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling StringEncoder.StringEncoder")
		else
			call error("Called StringEncoder.StringEncoder on invalid object.")
		endif
	endif
	call destroyStringEncoder(this_2)
endfunction

function Network_onDestroy takes integer this_2 returns nothing
	call dispatch_GamecacheBuffer_destroyGamecacheBuffer(Network_gcIntBuffer[this_2])
	call dispatch_GamecacheBuffer_destroyGamecacheBuffer(Network_gcRealBuffer[this_2])
	call dispatch_GamecacheBuffer_destroyGamecacheBuffer(Network_gcBooleanBuffer[this_2])
	call dispatch_GamecacheBuffer_destroyGamecacheBuffer(Network_gcStringBuffer[this_2])
	call dispatch_StringEncoder_destroyStringEncoder(Network_stringEncoder[this_2])
	call dispatch_HashBuffer_destroyHashBuffer(Network_dataBuffer[this_2])
	call dispatch_MetadataStore_destroyMetadataStore(Network_metadataStore[this_2])
	call dispatch_NetworkFinishedCallback_destroyNetworkFinishedCallback(Network_finishCallback[this_2])
endfunction

function dealloc_Network takes integer obj returns nothing
	if Network_typeId[obj] == 0 then
		call error("Double free: object of type Network")
	else
		set Network_firstFree = Network_firstFree + 1
		set Network_typeId[obj] = 0
	endif
endfunction

function destroyNetwork takes integer this_2 returns nothing
	call Network_onDestroy(this_2)
	call dealloc_Network(this_2)
endfunction

function dispatch_Network_destroyNetwork takes integer this_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.Network")
		else
			call error("Called Network.Network on invalid object.")
		endif
	endif
	call destroyNetwork(this_2)
endfunction

function Network_abort takes integer this_2 returns nothing
	set Network_currentState[this_2] = 2
	call dispatch_NetworkFinishedCallback_Network_NetworkFinishedCallback_onFinish(Network_finishCallback[this_2], 1, Network_dataBuffer[this_2])
	call dispatch_Network_destroyNetwork(this_2)
endfunction

function dispatch_Network_Network_Network_abort takes integer this_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.abort")
		else
			call error("Called Network.abort on invalid object.")
		endif
	endif
	call Network_abort(this_2)
endfunction

function alloc_LimitedExecuteAction_executeWhile_Network_Network_516 takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 761
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 761
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_Network_Network_533 takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 781
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 781
	endif
	return this_2
endfunction

function dealloc_LimitedExecuteAction takes integer obj returns nothing
	if LimitedExecuteAction_typeId[obj] == 0 then
		call error("Double free: object of type LimitedExecuteAction")
	else
		set LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree] = obj
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree + 1
		set LimitedExecuteAction_typeId[obj] = 0
	endif
endfunction

function destroyLimitedExecuteAction takes integer this_2 returns nothing
	call dealloc_LimitedExecuteAction(this_2)
endfunction

function dispatch_LimitedExecuteAction_destroyLimitedExecuteAction takes integer this_2 returns nothing
	if LimitedExecuteAction_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LimitedExecuteAction.LimitedExecuteAction")
		else
			call error("Called LimitedExecuteAction.LimitedExecuteAction on invalid object.")
		endif
	endif
	call destroyLimitedExecuteAction(this_2)
endfunction

function dealloc_LimitedExecuteCondition takes integer obj returns nothing
	if LimitedExecuteCondition_typeId[obj] == 0 then
		call error("Double free: object of type LimitedExecuteCondition")
	else
		set LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree] = obj
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree + 1
		set LimitedExecuteCondition_typeId[obj] = 0
	endif
endfunction

function destroyLimitedExecuteCondition takes integer this_2 returns nothing
	call dealloc_LimitedExecuteCondition(this_2)
endfunction

function dispatch_LimitedExecuteCondition_destroyLimitedExecuteCondition takes integer this_2 returns nothing
	if LimitedExecuteCondition_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LimitedExecuteCondition.LimitedExecuteCondition")
		else
			call error("Called LimitedExecuteCondition.LimitedExecuteCondition on invalid object.")
		endif
	endif
	call destroyLimitedExecuteCondition(this_2)
endfunction

function alloc_ForForceCallback_execute_Execute takes nothing returns integer
	local integer this_2
	if ForForceCallback_firstFree == 0 then
		if ForForceCallback_maxIndex < 32768 then
			set ForForceCallback_maxIndex = ForForceCallback_maxIndex + 1
			set this_2 = ForForceCallback_maxIndex
			set ForForceCallback_typeId[this_2] = 695
		else
			call error("Out of memory: Could not create ForForceCallback_execute_Execute.")
			set this_2 = 0
		endif
	else
		set ForForceCallback_firstFree = ForForceCallback_firstFree - 1
		set this_2 = ForForceCallback_nextFree[ForForceCallback_firstFree]
		set ForForceCallback_typeId[this_2] = 695
	endif
	return this_2
endfunction

function execute takes integer c returns nothing
	if  not try(c) then
		call error("execute: thread has crashed. caused by:\n| - " + ErrorHandling_lastError)
	endif
endfunction

function executeWhileInternal takes integer resetCount_2, integer condition_2, integer action_2 returns nothing
	local integer clVar = alloc_ForForceCallback_execute_Execute()
	set condition[clVar] = condition_2
	set resetCount[clVar] = resetCount_2
	set action[clVar] = action_2
	call execute(clVar)
endfunction

function executeWhile takes integer resetCount_2, integer condition_2, integer action_2 returns nothing
	call executeWhileInternal(resetCount_2, condition_2, action_2)
	call dispatch_LimitedExecuteCondition_destroyLimitedExecuteCondition(condition_2)
	call dispatch_LimitedExecuteAction_destroyLimitedExecuteAction(action_2)
endfunction

function Network_readBooleans takes integer this_2 returns nothing
	local integer temp = NetworkConfig_DATA_PER_EXECUTE
	local integer clVar = alloc_LimitedExecuteCondition_executeWhile_Network_Network_533()
	local integer clVar_2
	local integer temp_2
	set this_825[clVar] = this_2
	set temp_2 = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_Network_Network_516()
	set this_826[clVar_2] = this_2
	call executeWhile(temp, temp_2, clVar_2)
endfunction

function dispatch_Network_Network_Network_readBooleans takes integer this_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.readBooleans")
		else
			call error("Called Network.readBooleans on invalid object.")
		endif
	endif
	call Network_readBooleans(this_2)
endfunction

function alloc_LimitedExecuteAction_executeWhile_Network_Network_512 takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 757
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 757
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_Network_Network_529 takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 777
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 777
	endif
	return this_2
endfunction

function Network_readInts takes integer this_2 returns nothing
	local integer temp = NetworkConfig_DATA_PER_EXECUTE
	local integer clVar = alloc_LimitedExecuteCondition_executeWhile_Network_Network_529()
	local integer clVar_2
	local integer temp_2
	set this_817[clVar] = this_2
	set temp_2 = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_Network_Network_512()
	set this_818[clVar_2] = this_2
	call executeWhile(temp, temp_2, clVar_2)
endfunction

function dispatch_Network_Network_Network_readInts takes integer this_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.readInts")
		else
			call error("Called Network.readInts on invalid object.")
		endif
	endif
	call Network_readInts(this_2)
endfunction

function alloc_LimitedExecuteAction_executeWhile_Network_Network_514 takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 759
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 759
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_Network_Network_531 takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 779
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 779
	endif
	return this_2
endfunction

function Network_readReals takes integer this_2 returns nothing
	local integer temp = NetworkConfig_DATA_PER_EXECUTE
	local integer clVar = alloc_LimitedExecuteCondition_executeWhile_Network_Network_531()
	local integer clVar_2
	local integer temp_2
	set this_821[clVar] = this_2
	set temp_2 = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_Network_Network_514()
	set this_822[clVar_2] = this_2
	call executeWhile(temp, temp_2, clVar_2)
endfunction

function dispatch_Network_Network_Network_readReals takes integer this_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.readReals")
		else
			call error("Called Network.readReals on invalid object.")
		endif
	endif
	call Network_readReals(this_2)
endfunction

function alloc_LimitedExecuteAction_executeWhile_Network_Network_518 takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 763
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 763
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_Network_Network_535 takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 783
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 783
	endif
	return this_2
endfunction

function Network_readStrings takes integer this_2 returns nothing
	local integer temp = NetworkConfig_DATA_PER_EXECUTE
	local integer clVar = alloc_LimitedExecuteCondition_executeWhile_Network_Network_535()
	local integer clVar_2
	local integer temp_2
	set this_829[clVar] = this_2
	set temp_2 = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_Network_Network_518()
	set this_830[clVar_2] = this_2
	call executeWhile(temp, temp_2, clVar_2)
endfunction

function dispatch_Network_Network_Network_readStrings takes integer this_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.readStrings")
		else
			call error("Called Network.readStrings on invalid object.")
		endif
	endif
	call Network_readStrings(this_2)
endfunction

function gamecache_loadInt takes gamecache this_2, string missionKey, string key returns integer
	return GetStoredInteger(this_2, missionKey, key)
endfunction

function MetadataStore_loadValue takes integer this_2, string key returns integer
	return gamecache_loadInt(MetadataStore_META_CACHE, MetadataStore_mkey[this_2], key)
endfunction

function dispatch_MetadataStore_Metadata_MetadataStore_loadValue takes integer this_2, string key returns integer
	local integer Metadata_MetadataStore_loadValue_result
	if MetadataStore_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling MetadataStore.loadValue")
		else
			call error("Called MetadataStore.loadValue on invalid object.")
		endif
	endif
	set Metadata_MetadataStore_loadValue_result = MetadataStore_loadValue(this_2, key)
	return Metadata_MetadataStore_loadValue_result
endfunction

function MetadataStore_getCounts takes integer this_2 returns integer
	set MetadataStore_getCounts_return_ints = dispatch_MetadataStore_Metadata_MetadataStore_loadValue(this_2, MetadataStore_META_INTEGER_KEY)
	set MetadataStore_getCounts_return_reals = dispatch_MetadataStore_Metadata_MetadataStore_loadValue(this_2, MetadataStore_META_REAL_KEY)
	set MetadataStore_getCounts_return_booleans = dispatch_MetadataStore_Metadata_MetadataStore_loadValue(this_2, MetadataStore_META_BOOLEAN_KEY)
	set MetadataStore_getCounts_return_asciiInts = dispatch_MetadataStore_Metadata_MetadataStore_loadValue(this_2, MetadataStore_META_STRING_KEY)
	set MetadataStore_getCounts_return_syncs = dispatch_MetadataStore_Metadata_MetadataStore_loadValue(this_2, MetadataStore_META_SYNC_ROUNDS_KEY)
	return MetadataStore_getCounts_return_ints
endfunction

function dispatch_MetadataStore_Metadata_MetadataStore_getCounts takes integer this_2 returns integer
	local integer Metadata_MetadataStore_getCounts_result_ints
	local integer Metadata_MetadataStore_getCounts_result_reals
	local integer Metadata_MetadataStore_getCounts_result_booleans
	local integer Metadata_MetadataStore_getCounts_result_asciiInts
	local integer Metadata_MetadataStore_getCounts_result_syncs
	local integer tuple_temp
	local integer tuple_temp_2
	local integer tuple_temp_3
	local integer tuple_temp_4
	local integer tuple_temp_5
	if MetadataStore_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling MetadataStore.getCounts")
		else
			call error("Called MetadataStore.getCounts on invalid object.")
		endif
	endif
	set tuple_temp = MetadataStore_getCounts(this_2)
	set tuple_temp_2 = MetadataStore_getCounts_return_reals
	set tuple_temp_3 = MetadataStore_getCounts_return_booleans
	set tuple_temp_4 = MetadataStore_getCounts_return_asciiInts
	set tuple_temp_5 = MetadataStore_getCounts_return_syncs
	set Metadata_MetadataStore_getCounts_result_ints = tuple_temp
	set Metadata_MetadataStore_getCounts_result_reals = tuple_temp_2
	set Metadata_MetadataStore_getCounts_result_booleans = tuple_temp_3
	set Metadata_MetadataStore_getCounts_result_asciiInts = tuple_temp_4
	set Metadata_MetadataStore_getCounts_result_syncs = tuple_temp_5
	set dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_ints = Metadata_MetadataStore_getCounts_result_ints
	set dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_reals = Metadata_MetadataStore_getCounts_result_reals
	set dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_booleans = Metadata_MetadataStore_getCounts_result_booleans
	set dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_asciiInts = Metadata_MetadataStore_getCounts_result_asciiInts
	set dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_syncs = Metadata_MetadataStore_getCounts_result_syncs
	return dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_ints
endfunction

function Network_receiveMetadata takes integer this_2 returns nothing
	local integer tuple_temp
	local integer tuple_temp_2
	local integer tuple_temp_3
	local integer tuple_temp_4
	local integer tuple_temp_5
	if Player_localPlayer == Network_sender[this_2] then
		return
	endif
	if SafetyChecks_SAFETY_CHECKS_ENABLED and Network_currentState[this_2] != 1 then
		call error("Network: trying to receive metadata at a wrong time")
	endif
	set tuple_temp = dispatch_MetadataStore_Metadata_MetadataStore_getCounts(Network_metadataStore[this_2])
	set tuple_temp_2 = dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_reals
	set tuple_temp_3 = dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_booleans
	set tuple_temp_4 = dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_asciiInts
	set tuple_temp_5 = dispatch_MetadataStore_Metadata_MetadataStore_getCounts_return_syncs
	set Network_meta_ints[this_2] = tuple_temp
	set Network_meta_reals[this_2] = tuple_temp_2
	set Network_meta_booleans[this_2] = tuple_temp_3
	set Network_meta_asciiInts[this_2] = tuple_temp_4
	set Network_meta_syncs[this_2] = tuple_temp_5
	set Network_metaReceived[this_2] = true
endfunction

function dispatch_Network_Network_Network_receiveMetadata takes integer this_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.receiveMetadata")
		else
			call error("Called Network.receiveMetadata on invalid object.")
		endif
	endif
	call Network_receiveMetadata(this_2)
endfunction

function alloc_LimitedExecuteAction_executeWhile_StringEncoder_StringEncoder_523 takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 769
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_StringEncoder_StringEncoder.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 769
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_StringEncoder_StringEncoder_540 takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 789
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_StringEncoder_StringEncoder.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 789
	endif
	return this_2
endfunction

function StringEncoder_decode takes integer this_2 returns nothing
	local integer temp = NetworkConfig_DATA_PER_EXECUTE
	local integer clVar = alloc_LimitedExecuteCondition_executeWhile_StringEncoder_StringEncoder_540()
	local integer clVar_2
	local integer temp_2
	set this_841[clVar] = this_2
	set temp_2 = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_StringEncoder_StringEncoder_523()
	set this_842[clVar_2] = this_2
	call executeWhile(temp, temp_2, clVar_2)
endfunction

function dispatch_StringEncoder_StringEncoder_StringEncoder_decode takes integer this_2 returns nothing
	if StringEncoder_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling StringEncoder.decode")
		else
			call error("Called StringEncoder.decode on invalid object.")
		endif
	endif
	call StringEncoder_decode(this_2)
endfunction

function player_getController takes player this_2 returns mapcontrol
	return GetPlayerController(this_2)
endfunction

function player_getSlotState takes player this_2 returns playerslotstate
	return GetPlayerSlotState(this_2)
endfunction

function player_isIngame takes player this_2 returns boolean
	return player_getSlotState(this_2) == PLAYER_SLOT_STATE_PLAYING and player_getController(this_2) == MAP_CONTROL_USER
endfunction

function Network_receiveRound takes integer this_2 returns nothing
	local integer clVar
	local integer clVar_2
	local integer clVar_3
	local integer temp
	local integer temp_2
	if SafetyChecks_SAFETY_CHECKS_ENABLED and Network_currentState[this_2] != 1 then
		call error("Network: trying to receive round at the wrong time")
	endif
	if  not player_isIngame(Network_sender[this_2]) then
		call dispatch_Network_Network_Network_abort(this_2)
		return
	endif
	if  not Network_metaReceived[this_2] then
		call dispatch_Network_Network_Network_receiveMetadata(this_2)
	endif
	if Player_localPlayer != Network_sender[this_2] then
		call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_setMode(Network_dataBuffer[this_2], 1)
		call dispatch_Network_Network_Network_readInts(this_2)
		call dispatch_Network_Network_Network_readReals(this_2)
		call dispatch_Network_Network_Network_readBooleans(this_2)
		call dispatch_Network_Network_Network_readStrings(this_2)
		call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_setMode(Network_dataBuffer[this_2], 3)
	endif
	set Network_counters_syncs[this_2] = Network_counters_syncs[this_2] + 1
	if Network_counters_syncs[this_2] < Network_meta_syncs[this_2] then
		set clVar = alloc_ForForceCallback_execute_Network_Network()
		set this_832[clVar] = this_2
		call execute(clVar)
	else
		if Player_localPlayer != Network_sender[this_2] then
			call dispatch_StringEncoder_StringEncoder_StringEncoder_decode(Network_stringEncoder[this_2])
			call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_setMode(Network_dataBuffer[this_2], 1)
			set temp = NetworkConfig_DATA_PER_EXECUTE
			set clVar_2 = alloc_LimitedExecuteCondition_executeWhile_Network_Network_536()
			set this_833[clVar_2] = this_2
			set temp_2 = clVar_2
			set clVar_3 = alloc_LimitedExecuteAction_executeWhile_Network_Network_519()
			set this_834[clVar_3] = this_2
			call executeWhile(temp, temp_2, clVar_3)
		endif
		call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_setMode(Network_dataBuffer[this_2], 0)
		call dispatch_HashBuffer_HashBuffer_HashBuffer_resetRead(Network_dataBuffer[this_2])
		set Network_currentState[this_2] = 2
		call dispatch_NetworkFinishedCallback_Network_NetworkFinishedCallback_onFinish(Network_finishCallback[this_2], 0, Network_dataBuffer[this_2])
		call dispatch_Network_destroyNetwork(this_2)
	endif
endfunction

function dispatch_Network_Network_Network_receiveRound takes integer this_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.receiveRound")
		else
			call error("Called Network.receiveRound on invalid object.")
		endif
	endif
	call Network_receiveRound(this_2)
endfunction

function onSynchronized_onSynced_Network_Network takes integer this_2 returns nothing
	call dispatch_Network_Network_Network_receiveRound(this_831[this_2])
endfunction

function alloc_NetworkFinishedCallback_start_onSynced_PersistentStore_Persistable takes nothing returns integer
	local integer this_2
	if NetworkFinishedCallback_firstFree == 0 then
		if NetworkFinishedCallback_maxIndex < 32768 then
			set NetworkFinishedCallback_maxIndex = NetworkFinishedCallback_maxIndex + 1
			set this_2 = NetworkFinishedCallback_maxIndex
			set NetworkFinishedCallback_typeId[this_2] = 808
		else
			call error("Out of memory: Could not create NetworkFinishedCallback_start_onSynced_PersistentStore_Persistable.")
			set this_2 = 0
		endif
	else
		set NetworkFinishedCallback_firstFree = NetworkFinishedCallback_firstFree - 1
		set this_2 = NetworkFinishedCallback_nextFree[NetworkFinishedCallback_firstFree]
		set NetworkFinishedCallback_typeId[this_2] = 808
	endif
	return this_2
endfunction

function LLIterator_onDestroy takes integer this_2 returns nothing
endfunction

function dealloc_LLIterator takes integer obj returns nothing
	if LLIterator_typeId[obj] == 0 then
		call error("Double free: object of type LLIterator")
	else
		set LLIterator_nextFree[LLIterator_firstFree] = obj
		set LLIterator_firstFree = LLIterator_firstFree + 1
		set LLIterator_typeId[obj] = 0
	endif
endfunction

function destroyLLIterator takes integer this_2 returns nothing
	call LLIterator_onDestroy(this_2)
	call dealloc_LLIterator(this_2)
endfunction

function dispatch_LLIterator_destroyLLIterator takes integer this_2 returns nothing
	if LLIterator_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LLIterator.LLIterator")
		else
			call error("Called LLIterator.LLIterator on invalid object.")
		endif
	endif
	call destroyLLIterator(this_2)
endfunction

function LLIterator_close takes integer this_2 returns nothing
	if LLIterator_destroyOnClose[this_2] then
		call dispatch_LLIterator_destroyLLIterator(this_2)
	endif
endfunction

function LLIterator_hasNext takes integer this_2 returns boolean
	return LLEntry_next[LLIterator_current[this_2]] != LLIterator_dummy[this_2]
endfunction

function LLIterator_next takes integer this_2 returns integer
	set LLIterator_current[this_2] = LLEntry_next[LLIterator_current[this_2]]
	return LLEntry_elem[LLIterator_current[this_2]]
endfunction

function alloc_LLIterator takes nothing returns integer
	local integer this_2
	if LLIterator_firstFree == 0 then
		if LLIterator_maxIndex < 32768 then
			set LLIterator_maxIndex = LLIterator_maxIndex + 1
			set this_2 = LLIterator_maxIndex
			set LLIterator_typeId[this_2] = 746
		else
			call error("Out of memory: Could not create LLIterator.")
			set this_2 = 0
		endif
	else
		set LLIterator_firstFree = LLIterator_firstFree - 1
		set this_2 = LLIterator_nextFree[LLIterator_firstFree]
		set LLIterator_typeId[this_2] = 746
	endif
	return this_2
endfunction

function LinkedList_getDummy takes integer this_2 returns integer
	return LinkedList_dummy[this_2]
endfunction

function dispatch_LinkedList_LinkedList_LinkedList_getDummy takes integer this_2 returns integer
	local integer LinkedList_LinkedList_getDummy_result
	if LinkedList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LinkedList.getDummy")
		else
			call error("Called LinkedList.getDummy on invalid object.")
		endif
	endif
	set LinkedList_LinkedList_getDummy_result = LinkedList_getDummy(this_2)
	return LinkedList_LinkedList_getDummy_result
endfunction

function LLIterator_reset takes integer this_2 returns nothing
	set LLIterator_dummy[this_2] = dispatch_LinkedList_LinkedList_LinkedList_getDummy(LLIterator_parent[this_2])
	set LLIterator_current[this_2] = LLIterator_dummy[this_2]
endfunction

function dispatch_LLIterator_LinkedList_LLIterator_reset takes integer this_2 returns nothing
	if LLIterator_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LLIterator.reset")
		else
			call error("Called LLIterator.reset on invalid object.")
		endif
	endif
	call LLIterator_reset(this_2)
endfunction

function construct_LLIterator takes integer this_2, integer parent returns nothing
	set LLIterator_destroyOnClose[this_2] = true
	set LLIterator_parent[this_2] = parent
	call dispatch_LLIterator_LinkedList_LLIterator_reset(this_2)
endfunction

function new_LLIterator takes integer parent returns integer
	local integer this_2 = alloc_LLIterator()
	call construct_LLIterator(this_2, parent)
	return this_2
endfunction

function LinkedList_iterator takes integer this_2 returns integer
	return new_LLIterator(this_2)
endfunction

function dealloc_IOTask takes integer obj returns nothing
	if IOTask_typeId[obj] == 0 then
		call error("Double free: object of type IOTask")
	else
		set IOTask_nextFree[IOTask_firstFree] = obj
		set IOTask_firstFree = IOTask_firstFree + 1
		set IOTask_typeId[obj] = 0
	endif
endfunction

function destroyIOTask takes integer this_2 returns nothing
	call dealloc_IOTask(this_2)
endfunction

function dispatch_IOTask_destroyIOTask takes integer this_2 returns nothing
	if IOTask_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling IOTask.IOTask")
		else
			call error("Called IOTask.IOTask on invalid object.")
		endif
	endif
	call destroyIOTask(this_2)
endfunction

function LLBackIterator_onDestroy takes integer this_2 returns nothing
endfunction

function dealloc_LLBackIterator takes integer obj returns nothing
	if LLBackIterator_typeId[obj] == 0 then
		call error("Double free: object of type LLBackIterator")
	else
		set LLBackIterator_firstFree = LLBackIterator_firstFree + 1
		set LLBackIterator_typeId[obj] = 0
	endif
endfunction

function destroyLLBackIterator takes integer this_2 returns nothing
	call LLBackIterator_onDestroy(this_2)
	call dealloc_LLBackIterator(this_2)
endfunction

function dispatch_LLBackIterator_destroyLLBackIterator takes integer this_2 returns nothing
	if LLBackIterator_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LLBackIterator.LLBackIterator")
		else
			call error("Called LLBackIterator.LLBackIterator on invalid object.")
		endif
	endif
	call destroyLLBackIterator(this_2)
endfunction

function LinkedList_onDestroy takes integer this_2 returns nothing
	local integer current
	if LinkedList_staticItr[this_2] != 0 then
		call dispatch_LLIterator_destroyLLIterator(LinkedList_staticItr[this_2])
	endif
	if LinkedList_staticBackItr[this_2] != 0 then
		call dispatch_LLBackIterator_destroyLLBackIterator(LinkedList_staticBackItr[this_2])
	endif
	set current = LLEntry_next[LinkedList_dummy[this_2]]
	loop
		exitwhen  not (current != LinkedList_dummy[this_2])
		set current = LLEntry_next[current]
		call dispatch_LLEntry_destroyLLEntry(LLEntry_prev[current])
	endloop
	call dispatch_LLEntry_destroyLLEntry(LinkedList_dummy[this_2])
endfunction

function dealloc_LinkedList takes integer obj returns nothing
	if LinkedList_typeId[obj] == 0 then
		call error("Double free: object of type LinkedList")
	else
		set LinkedList_nextFree[LinkedList_firstFree] = obj
		set LinkedList_firstFree = LinkedList_firstFree + 1
		set LinkedList_typeId[obj] = 0
	endif
endfunction

function destroyLinkedList takes integer this_2 returns nothing
	call LinkedList_onDestroy(this_2)
	call dealloc_LinkedList(this_2)
endfunction

function dispatch_LinkedList_destroyLinkedList takes integer this_2 returns nothing
	if LinkedList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LinkedList.LinkedList")
		else
			call error("Called LinkedList.LinkedList on invalid object.")
		endif
	endif
	call destroyLinkedList(this_2)
endfunction

function AbstractIOTaskExecutor_onDestroy takes integer this_2 returns nothing
	local integer iterator = LinkedList_iterator(AbstractIOTaskExecutor_taskQueue[this_2])
	local integer task
	loop
		exitwhen  not LLIterator_hasNext(iterator)
		set task = LLIterator_next(iterator)
		call dispatch_IOTask_destroyIOTask(task)
	endloop
	call LLIterator_close(iterator)
	call dispatch_LinkedList_destroyLinkedList(AbstractIOTaskExecutor_taskQueue[this_2])
	if AbstractIOTaskExecutor_onCompleteTask[this_2] != 0 then
		call dispatch_IOTask_destroyIOTask(AbstractIOTaskExecutor_onCompleteTask[this_2])
	endif
endfunction

function dealloc_AbstractIOTaskExecutor takes integer obj returns nothing
	if IOTaskExecutor_typeId[obj] == 0 then
		call error("Double free: object of type AbstractIOTaskExecutor")
	else
		set IOTaskExecutor_firstFree = IOTaskExecutor_firstFree + 1
		set IOTaskExecutor_typeId[obj] = 0
	endif
endfunction

function destroyAbstractIOTaskExecutor takes integer this_2 returns nothing
	call AbstractIOTaskExecutor_onDestroy(this_2)
	call dealloc_AbstractIOTaskExecutor(this_2)
endfunction

function dealloc_IOTaskExecutor takes integer obj returns nothing
	if IOTaskExecutor_typeId[obj] == 0 then
		call error("Double free: object of type IOTaskExecutor")
	else
		set IOTaskExecutor_firstFree = IOTaskExecutor_firstFree + 1
		set IOTaskExecutor_typeId[obj] = 0
	endif
endfunction

function destroyIOTaskExecutor takes integer this_2 returns nothing
	call dealloc_IOTaskExecutor(this_2)
endfunction

function InstantIOTaskExecutor_onDestroy takes integer this_2 returns nothing
	call AbstractIOTaskExecutor_onDestroy(this_2)
endfunction

function dealloc_InstantIOTaskExecutor takes integer obj returns nothing
	if IOTaskExecutor_typeId[obj] == 0 then
		call error("Double free: object of type InstantIOTaskExecutor")
	else
		set IOTaskExecutor_firstFree = IOTaskExecutor_firstFree + 1
		set IOTaskExecutor_typeId[obj] = 0
	endif
endfunction

function destroyInstantIOTaskExecutor takes integer this_2 returns nothing
	call InstantIOTaskExecutor_onDestroy(this_2)
	call dealloc_InstantIOTaskExecutor(this_2)
endfunction

function TimedIOTaskExecutor_LinkedListModule_remove takes integer this_2 returns nothing
	set TimedIOTaskExecutor_LinkedListModule_size = TimedIOTaskExecutor_LinkedListModule_size - 1
	if this_2 != TimedIOTaskExecutor_LinkedListModule_first then
		set TimedIOTaskExecutor_LinkedListModule_next[TimedIOTaskExecutor_LinkedListModule_prev[this_2]] = TimedIOTaskExecutor_LinkedListModule_next[this_2]
	else
		set TimedIOTaskExecutor_LinkedListModule_first = TimedIOTaskExecutor_LinkedListModule_next[this_2]
	endif
	if this_2 != TimedIOTaskExecutor_LinkedListModule_last then
		set TimedIOTaskExecutor_LinkedListModule_prev[TimedIOTaskExecutor_LinkedListModule_next[this_2]] = TimedIOTaskExecutor_LinkedListModule_prev[this_2]
	else
		set TimedIOTaskExecutor_LinkedListModule_last = TimedIOTaskExecutor_LinkedListModule_prev[this_2]
	endif
endfunction

function dispatch_TimedIOTaskExecutor_IOTaskExecutor_TimedIOTaskExecutor_LinkedListModule_remove takes integer this_2 returns nothing
	if IOTaskExecutor_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling TimedIOTaskExecutor.remove")
		else
			call error("Called TimedIOTaskExecutor.remove on invalid object.")
		endif
	endif
	call TimedIOTaskExecutor_LinkedListModule_remove(this_2)
endfunction

function TimedIOTaskExecutor_onDestroy takes integer this_2 returns nothing
	call dispatch_TimedIOTaskExecutor_IOTaskExecutor_TimedIOTaskExecutor_LinkedListModule_remove(this_2)
	call AbstractIOTaskExecutor_onDestroy(this_2)
endfunction

function dealloc_TimedIOTaskExecutor takes integer obj returns nothing
	if IOTaskExecutor_typeId[obj] == 0 then
		call error("Double free: object of type TimedIOTaskExecutor")
	else
		set IOTaskExecutor_firstFree = IOTaskExecutor_firstFree + 1
		set IOTaskExecutor_typeId[obj] = 0
	endif
endfunction

function destroyTimedIOTaskExecutor takes integer this_2 returns nothing
	call TimedIOTaskExecutor_onDestroy(this_2)
	call dealloc_TimedIOTaskExecutor(this_2)
endfunction

function dispatch_IOTaskExecutor_destroyIOTaskExecutor takes integer this_2 returns nothing
	if IOTaskExecutor_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling IOTaskExecutor.IOTaskExecutor")
		else
			call error("Called IOTaskExecutor.IOTaskExecutor on invalid object.")
		endif
	endif
	if IOTaskExecutor_typeId[this_2] <= 726 then
		if IOTaskExecutor_typeId[this_2] <= 725 then
			call destroyIOTaskExecutor(this_2)
		else
			call destroyAbstractIOTaskExecutor(this_2)
		endif
	elseif IOTaskExecutor_typeId[this_2] <= 727 then
		call destroyInstantIOTaskExecutor(this_2)
	else
		call destroyTimedIOTaskExecutor(this_2)
	endif
endfunction

function AbstractFile_onDestroy takes integer this_2 returns nothing
	call dispatch_HashBuffer_destroyHashBuffer(AbstractFile_buffer[this_2])
	call dispatch_IOTaskExecutor_destroyIOTaskExecutor(AbstractFile_executor[this_2])
endfunction

function FileReader_onDestroy takes integer this_2 returns nothing
	call AbstractFile_onDestroy(this_2)
endfunction

function dealloc_FileReader takes integer obj returns nothing
	if AbstractFile_typeId[obj] == 0 then
		call error("Double free: object of type FileReader")
	else
		set AbstractFile_firstFree = AbstractFile_firstFree + 1
		set AbstractFile_typeId[obj] = 0
	endif
endfunction

function destroyFileReader takes integer this_2 returns nothing
	call FileReader_onDestroy(this_2)
	call dealloc_FileReader(this_2)
endfunction

function dispatch_FileReader_destroyFileReader takes integer this_2 returns nothing
	if AbstractFile_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling FileReader.FileReader")
		else
			call error("Called FileReader.FileReader on invalid object.")
		endif
	endif
	call destroyFileReader(this_2)
endfunction

function alloc_LimitedExecuteAction_executeWhile_Network_Network takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 755
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 755
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_Network_Network takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 775
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 775
	endif
	return this_2
endfunction

function HashBuffer_getBooleanCount takes integer this_2 returns integer
	return HashBuffer_booleanWriteIndex[this_2] + 1
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_getBooleanCount takes integer this_2 returns integer
	local integer HashBuffer_HashBuffer_getBooleanCount_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.getBooleanCount")
		else
			call error("Called HashBuffer.getBooleanCount on invalid object.")
		endif
	endif
	set HashBuffer_HashBuffer_getBooleanCount_result = HashBuffer_getBooleanCount(this_2)
	return HashBuffer_HashBuffer_getBooleanCount_result
endfunction

function HashBuffer_getIntCount takes integer this_2 returns integer
	return HashBuffer_integerWriteIndex[this_2] + 1
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_getIntCount takes integer this_2 returns integer
	local integer HashBuffer_HashBuffer_getIntCount_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.getIntCount")
		else
			call error("Called HashBuffer.getIntCount on invalid object.")
		endif
	endif
	set HashBuffer_HashBuffer_getIntCount_result = HashBuffer_getIntCount(this_2)
	return HashBuffer_HashBuffer_getIntCount_result
endfunction

function HashBuffer_getRealCount takes integer this_2 returns integer
	return HashBuffer_realWriteIndex[this_2] + 1
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_getRealCount takes integer this_2 returns integer
	local integer HashBuffer_HashBuffer_getRealCount_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.getRealCount")
		else
			call error("Called HashBuffer.getRealCount on invalid object.")
		endif
	endif
	set HashBuffer_HashBuffer_getRealCount_result = HashBuffer_getRealCount(this_2)
	return HashBuffer_HashBuffer_getRealCount_result
endfunction

function gamecache_saveInt takes gamecache this_2, string missionKey, string key, integer value_2 returns nothing
	call StoreInteger(this_2, missionKey, key, value_2)
endfunction

function MetadataStore_saveValue takes integer this_2, string key, integer value_2 returns nothing
	call gamecache_saveInt(MetadataStore_META_CACHE, MetadataStore_mkey[this_2], key, value_2)
endfunction

function dispatch_MetadataStore_Metadata_MetadataStore_saveValue takes integer this_2, string key, integer value_2 returns nothing
	if MetadataStore_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling MetadataStore.saveValue")
		else
			call error("Called MetadataStore.saveValue on invalid object.")
		endif
	endif
	call MetadataStore_saveValue(this_2, key, value_2)
endfunction

function MetadataStore_setCounts takes integer this_2, integer data_ints, integer data_reals, integer data_booleans, integer data_asciiInts, integer data_syncs returns nothing
	call dispatch_MetadataStore_Metadata_MetadataStore_saveValue(this_2, MetadataStore_META_INTEGER_KEY, data_ints)
	call dispatch_MetadataStore_Metadata_MetadataStore_saveValue(this_2, MetadataStore_META_REAL_KEY, data_reals)
	call dispatch_MetadataStore_Metadata_MetadataStore_saveValue(this_2, MetadataStore_META_BOOLEAN_KEY, data_booleans)
	call dispatch_MetadataStore_Metadata_MetadataStore_saveValue(this_2, MetadataStore_META_STRING_KEY, data_asciiInts)
	call dispatch_MetadataStore_Metadata_MetadataStore_saveValue(this_2, MetadataStore_META_SYNC_ROUNDS_KEY, data_syncs)
endfunction

function dispatch_MetadataStore_Metadata_MetadataStore_setCounts takes integer this_2, integer data_ints, integer data_reals, integer data_booleans, integer data_asciiInts, integer data_syncs returns nothing
	if MetadataStore_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling MetadataStore.setCounts")
		else
			call error("Called MetadataStore.setCounts on invalid object.")
		endif
	endif
	call MetadataStore_setCounts(this_2, data_ints, data_reals, data_booleans, data_asciiInts, data_syncs)
endfunction

function GamecacheKeys_get takes integer i_2 returns string
	if i_2 >= GamecacheKeys_count then
		call error("Network: trying to get invalid GC key")
	endif
	return GamecacheKeys_keys[i_2]
endfunction

function gamecache_syncInt takes gamecache this_2, string missionKey, string key returns nothing
	call SyncStoredInteger(this_2, missionKey, key)
endfunction

function MetadataStore_sync takes integer this_2 returns nothing
	local integer i_2 = 0
	local integer temp = MetadataStore_META_COUNT - 1
	loop
		exitwhen i_2 > temp
		call gamecache_syncInt(MetadataStore_META_CACHE, MetadataStore_mkey[this_2], GamecacheKeys_get(i_2))
		set i_2 = i_2 + 1
	endloop
endfunction

function dispatch_MetadataStore_Metadata_MetadataStore_sync takes integer this_2 returns nothing
	if MetadataStore_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling MetadataStore.sync")
		else
			call error("Called MetadataStore.sync on invalid object.")
		endif
	endif
	call MetadataStore_sync(this_2)
endfunction

function alloc_SynchronizationCallback_onSynced_Network_Network takes nothing returns integer
	local integer this_2
	if SynchronizationCallback_firstFree == 0 then
		if SynchronizationCallback_maxIndex < 32768 then
			set SynchronizationCallback_maxIndex = SynchronizationCallback_maxIndex + 1
			set this_2 = SynchronizationCallback_maxIndex
			set SynchronizationCallback_typeId[this_2] = 848
		else
			call error("Out of memory: Could not create SynchronizationCallback_onSynced_Network_Network.")
			set this_2 = 0
		endif
	else
		set SynchronizationCallback_firstFree = SynchronizationCallback_firstFree - 1
		set this_2 = SynchronizationCallback_nextFree[SynchronizationCallback_firstFree]
		set SynchronizationCallback_typeId[this_2] = 848
	endif
	return this_2
endfunction

function GamecacheBuffer_reset takes integer this_2 returns nothing
	set GamecacheBuffer_writeIndex[this_2] = 0
	set GamecacheBuffer_readIndex[this_2] = 0
endfunction

function dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_reset takes integer this_2 returns nothing
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.reset")
		else
			call error("Called GamecacheBuffer.reset on invalid object.")
		endif
	endif
	call GamecacheBuffer_reset(this_2)
endfunction

function alloc_LimitedExecuteAction_executeWhile_GamecacheBuffer_GamecacheBuffer takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 754
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_GamecacheBuffer_GamecacheBuffer.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 754
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_GamecacheBuffer_GamecacheBuffer takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 774
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_GamecacheBuffer_GamecacheBuffer.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 774
	endif
	return this_2
endfunction

function alloc_SynchronizerFunction_GamecacheBuffer_GamecacheBuffer takes nothing returns integer
	local integer this_2
	if SynchronizerFunction_firstFree == 0 then
		if SynchronizerFunction_maxIndex < 32768 then
			set SynchronizerFunction_maxIndex = SynchronizerFunction_maxIndex + 1
			set this_2 = SynchronizerFunction_maxIndex
			set SynchronizerFunction_typeId[this_2] = 852
		else
			call error("Out of memory: Could not create SynchronizerFunction_GamecacheBuffer_GamecacheBuffer.")
			set this_2 = 0
		endif
	else
		set SynchronizerFunction_firstFree = SynchronizerFunction_firstFree - 1
		set this_2 = SynchronizerFunction_nextFree[SynchronizerFunction_firstFree]
		set SynchronizerFunction_typeId[this_2] = 852
	endif
	return this_2
endfunction

function alloc_SynchronizerFunction_GamecacheBuffer_GamecacheBuffer_550 takes nothing returns integer
	local integer this_2
	if SynchronizerFunction_firstFree == 0 then
		if SynchronizerFunction_maxIndex < 32768 then
			set SynchronizerFunction_maxIndex = SynchronizerFunction_maxIndex + 1
			set this_2 = SynchronizerFunction_maxIndex
			set SynchronizerFunction_typeId[this_2] = 853
		else
			call error("Out of memory: Could not create SynchronizerFunction_GamecacheBuffer_GamecacheBuffer.")
			set this_2 = 0
		endif
	else
		set SynchronizerFunction_firstFree = SynchronizerFunction_firstFree - 1
		set this_2 = SynchronizerFunction_nextFree[SynchronizerFunction_firstFree]
		set SynchronizerFunction_typeId[this_2] = 853
	endif
	return this_2
endfunction

function alloc_SynchronizerFunction_GamecacheBuffer_GamecacheBuffer_551 takes nothing returns integer
	local integer this_2
	if SynchronizerFunction_firstFree == 0 then
		if SynchronizerFunction_maxIndex < 32768 then
			set SynchronizerFunction_maxIndex = SynchronizerFunction_maxIndex + 1
			set this_2 = SynchronizerFunction_maxIndex
			set SynchronizerFunction_typeId[this_2] = 854
		else
			call error("Out of memory: Could not create SynchronizerFunction_GamecacheBuffer_GamecacheBuffer.")
			set this_2 = 0
		endif
	else
		set SynchronizerFunction_firstFree = SynchronizerFunction_firstFree - 1
		set this_2 = SynchronizerFunction_nextFree[SynchronizerFunction_firstFree]
		set SynchronizerFunction_typeId[this_2] = 854
	endif
	return this_2
endfunction

function GamecacheBuffer_getSynchronizerFunction takes integer this_2 returns integer
	local integer temp = GamecacheBuffer_bufferType[this_2]
	local integer synchronizer_2
	local integer clVar
	local integer clVar_2
	local integer clVar_3
	if temp == 0 then
		set clVar = alloc_SynchronizerFunction_GamecacheBuffer_GamecacheBuffer()
		set this_808[clVar] = this_2
		set synchronizer_2 = clVar
	elseif temp == 1 then
		set clVar_2 = alloc_SynchronizerFunction_GamecacheBuffer_GamecacheBuffer_550()
		set this_809[clVar_2] = this_2
		set synchronizer_2 = clVar_2
	else
		set clVar_3 = alloc_SynchronizerFunction_GamecacheBuffer_GamecacheBuffer_551()
		set this_810[clVar_3] = this_2
		set synchronizer_2 = clVar_3
	endif
	return synchronizer_2
endfunction

function dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_getSynchronizerFunction takes integer this_2 returns integer
	local integer GamecacheBuffer_GamecacheBuffer_getSynchronizerFunction_result
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.getSynchronizerFunction")
		else
			call error("Called GamecacheBuffer.getSynchronizerFunction on invalid object.")
		endif
	endif
	set GamecacheBuffer_GamecacheBuffer_getSynchronizerFunction_result = GamecacheBuffer_getSynchronizerFunction(this_2)
	return GamecacheBuffer_GamecacheBuffer_getSynchronizerFunction_result
endfunction

function dealloc_SynchronizerFunction takes integer obj returns nothing
	if SynchronizerFunction_typeId[obj] == 0 then
		call error("Double free: object of type SynchronizerFunction")
	else
		set SynchronizerFunction_nextFree[SynchronizerFunction_firstFree] = obj
		set SynchronizerFunction_firstFree = SynchronizerFunction_firstFree + 1
		set SynchronizerFunction_typeId[obj] = 0
	endif
endfunction

function destroySynchronizerFunction takes integer this_2 returns nothing
	call dealloc_SynchronizerFunction(this_2)
endfunction

function dispatch_SynchronizerFunction_destroySynchronizerFunction takes integer this_2 returns nothing
	if SynchronizerFunction_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling SynchronizerFunction.SynchronizerFunction")
		else
			call error("Called SynchronizerFunction.SynchronizerFunction on invalid object.")
		endif
	endif
	call destroySynchronizerFunction(this_2)
endfunction

function GamecacheBuffer_sync takes integer this_2 returns nothing
	local integer synchronizer_2 = dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_getSynchronizerFunction(this_2)
	local integer clVar
	local integer clVar_2
	local integer temp
	local integer temp_2
	set GamecacheBuffer_syncCounter[this_2] = 0
	set temp = NetworkConfig_DATA_PER_EXECUTE
	set clVar = alloc_LimitedExecuteCondition_executeWhile_GamecacheBuffer_GamecacheBuffer()
	set this_811[clVar] = this_2
	set temp_2 = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_GamecacheBuffer_GamecacheBuffer()
	set synchronizer_788[clVar_2] = synchronizer_2
	set this_812[clVar_2] = this_2
	call executeWhile(temp, temp_2, clVar_2)
	call dispatch_SynchronizerFunction_destroySynchronizerFunction(synchronizer_2)
endfunction

function dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_sync takes integer this_2 returns nothing
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.sync")
		else
			call error("Called GamecacheBuffer.sync on invalid object.")
		endif
	endif
	call GamecacheBuffer_sync(this_2)
endfunction

function alloc_LimitedExecuteAction_executeWhile_Network_Network_515 takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 760
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 760
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_Network_Network_532 takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 780
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 780
	endif
	return this_2
endfunction

function Network_writeBooleans takes integer this_2 returns nothing
	local integer temp = NetworkConfig_DATA_PER_EXECUTE
	local integer clVar = alloc_LimitedExecuteCondition_executeWhile_Network_Network_532()
	local integer clVar_2
	local integer temp_2
	set this_823[clVar] = this_2
	set temp_2 = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_Network_Network_515()
	set this_824[clVar_2] = this_2
	call executeWhile(temp, temp_2, clVar_2)
endfunction

function dispatch_Network_Network_Network_writeBooleans takes integer this_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.writeBooleans")
		else
			call error("Called Network.writeBooleans on invalid object.")
		endif
	endif
	call Network_writeBooleans(this_2)
endfunction

function alloc_LimitedExecuteAction_executeWhile_Network_Network_511 takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 756
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 756
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_Network_Network_528 takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 776
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 776
	endif
	return this_2
endfunction

function Network_writeInts takes integer this_2 returns nothing
	local integer temp = NetworkConfig_DATA_PER_EXECUTE
	local integer clVar = alloc_LimitedExecuteCondition_executeWhile_Network_Network_528()
	local integer clVar_2
	local integer temp_2
	set this_815[clVar] = this_2
	set temp_2 = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_Network_Network_511()
	set this_816[clVar_2] = this_2
	call executeWhile(temp, temp_2, clVar_2)
endfunction

function dispatch_Network_Network_Network_writeInts takes integer this_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.writeInts")
		else
			call error("Called Network.writeInts on invalid object.")
		endif
	endif
	call Network_writeInts(this_2)
endfunction

function alloc_LimitedExecuteAction_executeWhile_Network_Network_513 takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 758
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 758
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_Network_Network_530 takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 778
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 778
	endif
	return this_2
endfunction

function Network_writeReals takes integer this_2 returns nothing
	local integer temp = NetworkConfig_DATA_PER_EXECUTE
	local integer clVar = alloc_LimitedExecuteCondition_executeWhile_Network_Network_530()
	local integer clVar_2
	local integer temp_2
	set this_819[clVar] = this_2
	set temp_2 = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_Network_Network_513()
	set this_820[clVar_2] = this_2
	call executeWhile(temp, temp_2, clVar_2)
endfunction

function dispatch_Network_Network_Network_writeReals takes integer this_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.writeReals")
		else
			call error("Called Network.writeReals on invalid object.")
		endif
	endif
	call Network_writeReals(this_2)
endfunction

function alloc_LimitedExecuteAction_executeWhile_Network_Network_517 takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 762
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 762
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_Network_Network_534 takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 782
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_Network_Network.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 782
	endif
	return this_2
endfunction

function Network_writeStrings takes integer this_2 returns nothing
	local integer temp = NetworkConfig_DATA_PER_EXECUTE
	local integer clVar = alloc_LimitedExecuteCondition_executeWhile_Network_Network_534()
	local integer clVar_2
	local integer temp_2
	set this_827[clVar] = this_2
	set temp_2 = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_Network_Network_517()
	set this_828[clVar_2] = this_2
	call executeWhile(temp, temp_2, clVar_2)
endfunction

function dispatch_Network_Network_Network_writeStrings takes integer this_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.writeStrings")
		else
			call error("Called Network.writeStrings on invalid object.")
		endif
	endif
	call Network_writeStrings(this_2)
endfunction

function SimpleSynchronizer_onSynced takes integer this_2, integer callback_2 returns nothing
	set SimpleSynchronizer_callback[this_2] = callback_2
endfunction

function dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_onSynced takes integer this_2, integer callback_2 returns nothing
	if SimpleSynchronizer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling SimpleSynchronizer.onSynced")
		else
			call error("Called SimpleSynchronizer.onSynced on invalid object.")
		endif
	endif
	call SimpleSynchronizer_onSynced(this_2, callback_2)
endfunction

function SimpleSynchronizer_addOfflinePlayers takes integer this_2 returns nothing
	local integer i_2 = 0
	local integer temp = bj_MAX_PLAYER_SLOTS - 1
	local integer tuple_temp
	loop
		exitwhen i_2 > temp
		if  not player_isIngame(Player_players[i_2]) then
			set tuple_temp = bitset_add(SimpleSynchronizer_syncedPlayers_val[this_2], i_2)
			set SimpleSynchronizer_syncedPlayers_val[this_2] = tuple_temp
		endif
		set i_2 = i_2 + 1
	endloop
endfunction

function dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_addOfflinePlayers takes integer this_2 returns nothing
	if SimpleSynchronizer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling SimpleSynchronizer.addOfflinePlayers")
		else
			call error("Called SimpleSynchronizer.addOfflinePlayers on invalid object.")
		endif
	endif
	call SimpleSynchronizer_addOfflinePlayers(this_2)
endfunction

function alloc_ForGroupCallback_forUnitsSelected_SyncSimple takes nothing returns integer
	local integer this_2
	if ForGroupCallback_firstFree == 0 then
		if ForGroupCallback_maxIndex < 32768 then
			set ForGroupCallback_maxIndex = ForGroupCallback_maxIndex + 1
			set this_2 = ForGroupCallback_maxIndex
			set ForGroupCallback_typeId[this_2] = 710
		else
			call error("Out of memory: Could not create ForGroupCallback_forUnitsSelected_SyncSimple.")
			set this_2 = 0
		endif
	else
		set ForGroupCallback_firstFree = ForGroupCallback_firstFree - 1
		set this_2 = ForGroupCallback_nextFree[ForGroupCallback_firstFree]
		set ForGroupCallback_typeId[this_2] = 710
	endif
	return this_2
endfunction

function dealloc_ForGroupCallback takes integer obj returns nothing
	if ForGroupCallback_typeId[obj] == 0 then
		call error("Double free: object of type ForGroupCallback")
	else
		set ForGroupCallback_nextFree[ForGroupCallback_firstFree] = obj
		set ForGroupCallback_firstFree = ForGroupCallback_firstFree + 1
		set ForGroupCallback_typeId[obj] = 0
	endif
endfunction

function destroyForGroupCallback takes integer this_2 returns nothing
	call dealloc_ForGroupCallback(this_2)
endfunction

function dispatch_ForGroupCallback_destroyForGroupCallback takes integer this_2 returns nothing
	if ForGroupCallback_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling ForGroupCallback.ForGroupCallback")
		else
			call error("Called ForGroupCallback.ForGroupCallback on invalid object.")
		endif
	endif
	call destroyForGroupCallback(this_2)
endfunction

function group_clear takes group this_2 returns nothing
	call GroupClear(this_2)
endfunction

function popCallback takes nothing returns nothing
	call group_clear(ClosureForGroups_DUMMY_GROUP)
	set ClosureForGroups_tempCallbacksCount = ClosureForGroups_tempCallbacksCount - 1
	call dispatch_ForGroupCallback_destroyForGroupCallback(ClosureForGroups_tempCallbacks[ClosureForGroups_tempCallbacksCount])
endfunction

function pushCallback takes integer c returns nothing
	set ClosureForGroups_tempCallbacks[ClosureForGroups_tempCallbacksCount] = c
	set ClosureForGroups_tempCallbacksCount = ClosureForGroups_tempCallbacksCount + 1
	set ClosureForGroups_iterCount = 0
	set ClosureForGroups_maxCount = Integer_INT_MAX
endfunction

function forUnitsSelected takes player p, integer c returns nothing
	call pushCallback(c)
	call GroupEnumUnitsSelected(ClosureForGroups_DUMMY_GROUP, p, ClosureForGroups_filter)
	call popCallback()
endfunction

function group_enumUnitsSelected takes group this_2, player p, boolexpr filter returns nothing
	call GroupEnumUnitsSelected(this_2, p, filter)
endfunction

function player_select takes player this_2, unit u returns nothing
	if Player_localPlayer == this_2 then
		call SelectUnit(u, true)
	endif
endfunction

function player_unselect takes player this_2, unit u returns nothing
	if Player_localPlayer == this_2 then
		call SelectUnit(u, false)
	endif
endfunction

function player_onceSelect takes player this_2, unit what returns nothing
	local integer clVar
	local player temp
	call group_enumUnitsSelected(Group_ENUM_GROUP, this_2, null)
	set SyncSimple_last = null
	set SyncSimple_count = 0
	set temp = this_2
	set clVar = alloc_ForGroupCallback_forUnitsSelected_SyncSimple()
	call forUnitsSelected(temp, clVar)
	if SyncSimple_count >= 12 then
		call player_unselect(this_2, SyncSimple_last)
	endif
	if Player_localPlayer == this_2 then
		call player_select(this_2, what)
		call player_unselect(this_2, what)
	endif
	if SyncSimple_count >= 12 then
		call player_select(this_2, SyncSimple_last)
	endif
	set temp = null
endfunction

function SimpleSynchronizer_sync takes integer this_2 returns nothing
	call dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_addOfflinePlayers(this_2)
	call player_onceSelect(Player_localPlayer, SimpleSynchronizer_dummy[this_2])
endfunction

function dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_sync takes integer this_2 returns nothing
	if SimpleSynchronizer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling SimpleSynchronizer.sync")
		else
			call error("Called SimpleSynchronizer.sync on invalid object.")
		endif
	endif
	call SimpleSynchronizer_sync(this_2)
endfunction

function gamecache_flushMission takes gamecache this_2, string missionKey returns nothing
	call FlushStoredMission(this_2, missionKey)
endfunction

function alloc_SimpleSynchronizer takes nothing returns integer
	local integer this_2
	if SimpleSynchronizer_firstFree == 0 then
		if SimpleSynchronizer_maxIndex < 32768 then
			set SimpleSynchronizer_maxIndex = SimpleSynchronizer_maxIndex + 1
			set this_2 = SimpleSynchronizer_maxIndex
			set SimpleSynchronizer_typeId[this_2] = 827
		else
			call error("Out of memory: Could not create SimpleSynchronizer.")
			set this_2 = 0
		endif
	else
		set SimpleSynchronizer_firstFree = SimpleSynchronizer_firstFree - 1
		set this_2 = SimpleSynchronizer_nextFree[SimpleSynchronizer_firstFree]
		set SimpleSynchronizer_typeId[this_2] = 827
	endif
	return this_2
endfunction

function construct_SimpleSynchronizer_LinkedListModule takes integer this_2 returns nothing
	set SimpleSynchronizer_LinkedListModule_size = SimpleSynchronizer_LinkedListModule_size + 1
	if SimpleSynchronizer_LinkedListModule_size == 1 then
		set SimpleSynchronizer_LinkedListModule_first = this_2
		set SimpleSynchronizer_LinkedListModule_prev[this_2] = 0
	else
		set SimpleSynchronizer_LinkedListModule_prev[this_2] = SimpleSynchronizer_LinkedListModule_last
		set SimpleSynchronizer_LinkedListModule_next[SimpleSynchronizer_LinkedListModule_last] = this_2
		set SimpleSynchronizer_LinkedListModule_prev[SimpleSynchronizer_LinkedListModule_first] = this_2
	endif
	set SimpleSynchronizer_LinkedListModule_next[this_2] = 0
	set SimpleSynchronizer_LinkedListModule_last = this_2
endfunction

function emptyBitset takes nothing returns integer
	set emptyBitset_return_val = 0
	return emptyBitset_return_val
endfunction

function createUnit takes player p, integer unitId, real pos_x, real pos_y, real facing_radians returns unit
	return CreateUnit(p, unitId, pos_x, pos_y, angle_degrees(facing_radians))
endfunction

function real_fromDeg takes real this_2 returns real
	set real_fromDeg_return_radians = this_2 * Angle_DEGTORAD
	return real_fromDeg_return_radians
endfunction

function unit_addAbility takes unit this_2, integer abil returns boolean
	return UnitAddAbility(this_2, abil)
endfunction

function getDummy takes nothing returns unit
	local unit receiver = createUnit(Basics_DUMMY_PLAYER, 1751543663, MapBounds_boundMax_x, MapBounds_boundMax_y, real_fromDeg(0.))
	local unit receiver_2
	local unit receiver_3
	call unit_addAbility(receiver, Basics_GHOST_VIS_ID)
	set receiver_2 = receiver
	call unit_pause(receiver_2)
	set receiver_3 = receiver_2
	call unit_setScale(receiver_3, 0.)
	set getDummytempReturn = receiver_3
	set receiver = null
	set receiver_2 = null
	set receiver_3 = null
	return getDummytempReturn
endfunction

function construct_SimpleSynchronizer takes integer this_2 returns nothing
	local integer tuple_temp = emptyBitset()
	set SimpleSynchronizer_syncedPlayers_val[this_2] = tuple_temp
	set SimpleSynchronizer_callback[this_2] = 0
	call construct_SimpleSynchronizer_LinkedListModule(this_2)
	set SimpleSynchronizer_dummy[this_2] = getDummy()
	set SimpleSynchronizer_reverseLookup[unit_getIndex(SimpleSynchronizer_dummy[this_2])] = this_2
endfunction

function new_SimpleSynchronizer takes nothing returns integer
	local integer this_2 = alloc_SimpleSynchronizer()
	call construct_SimpleSynchronizer(this_2)
	return this_2
endfunction

function Network_sendRound takes integer this_2 returns nothing
	local integer synchronizer_2
	local integer clVar
	local integer temp
	if SafetyChecks_SAFETY_CHECKS_ENABLED and Network_currentState[this_2] != 1 then
		call error("Network: trying to send round at the wrong time")
	endif
	set synchronizer_2 = new_SimpleSynchronizer()
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_reset(Network_gcIntBuffer[this_2])
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_reset(Network_gcRealBuffer[this_2])
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_reset(Network_gcBooleanBuffer[this_2])
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_reset(Network_gcStringBuffer[this_2])
	call gamecache_flushMission(Network_DATA_CACHE, Network_mkey[this_2])
	call gamecache_flushMission(Network_STRING_CACHE, Network_mkey[this_2])
	if Player_localPlayer == Network_sender[this_2] then
		call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_setMode(Network_dataBuffer[this_2], 0)
		call dispatch_Network_Network_Network_writeInts(this_2)
		call dispatch_Network_Network_Network_writeBooleans(this_2)
		call dispatch_Network_Network_Network_writeReals(this_2)
		call dispatch_Network_Network_Network_writeStrings(this_2)
		call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_setMode(Network_dataBuffer[this_2], 3)
		call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_sync(Network_gcIntBuffer[this_2])
		call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_sync(Network_gcBooleanBuffer[this_2])
		call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_sync(Network_gcRealBuffer[this_2])
		call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_sync(Network_gcStringBuffer[this_2])
	endif
	call dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_sync(synchronizer_2)
	set temp = synchronizer_2
	set clVar = alloc_SynchronizationCallback_onSynced_Network_Network()
	set this_831[clVar] = this_2
	call dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_onSynced(temp, clVar)
endfunction

function dispatch_Network_Network_Network_sendRound takes integer this_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.sendRound")
		else
			call error("Called Network.sendRound on invalid object.")
		endif
	endif
	call Network_sendRound(this_2)
endfunction

function alloc_LimitedExecuteAction_executeWhile_StringEncoder_StringEncoder_522 takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 768
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_StringEncoder_StringEncoder.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 768
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_StringEncoder_StringEncoder_539 takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 788
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_StringEncoder_StringEncoder.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 788
	endif
	return this_2
endfunction

function StringEncoder_encode takes integer this_2 returns nothing
	local integer temp = NetworkConfig_DATA_PER_EXECUTE
	local integer clVar = alloc_LimitedExecuteCondition_executeWhile_StringEncoder_StringEncoder_539()
	local integer clVar_2
	local integer temp_2
	set this_839[clVar] = this_2
	set temp_2 = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_StringEncoder_StringEncoder_522()
	set this_840[clVar_2] = this_2
	call executeWhile(temp, temp_2, clVar_2)
endfunction

function dispatch_StringEncoder_StringEncoder_StringEncoder_encode takes integer this_2 returns nothing
	if StringEncoder_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling StringEncoder.encode")
		else
			call error("Called StringEncoder.encode on invalid object.")
		endif
	endif
	call StringEncoder_encode(this_2)
endfunction

function StringEncoder_getIntCount takes integer this_2 returns integer
	return StringEncoder_asciiIntCount[this_2]
endfunction

function dispatch_StringEncoder_StringEncoder_StringEncoder_getIntCount takes integer this_2 returns integer
	local integer StringEncoder_StringEncoder_getIntCount_result
	if StringEncoder_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling StringEncoder.getIntCount")
		else
			call error("Called StringEncoder.getIntCount on invalid object.")
		endif
	endif
	set StringEncoder_StringEncoder_getIntCount_result = StringEncoder_getIntCount(this_2)
	return StringEncoder_StringEncoder_getIntCount_result
endfunction

function max_4 takes integer numbers_0, integer numbers_1, integer numbers_2, integer numbers_3 returns integer
	local integer maxNumber = Integer_INT_MIN
	local integer cond_result
	if numbers_0 > maxNumber then
		set cond_result = numbers_0
	else
		set cond_result = maxNumber
	endif
	set maxNumber = cond_result
	if numbers_1 > maxNumber then
		set cond_result = numbers_1
	else
		set cond_result = maxNumber
	endif
	set maxNumber = cond_result
	if numbers_2 > maxNumber then
		set cond_result = numbers_2
	else
		set cond_result = maxNumber
	endif
	set maxNumber = cond_result
	if numbers_3 > maxNumber then
		set cond_result = numbers_3
	else
		set cond_result = maxNumber
	endif
	set maxNumber = cond_result
	return maxNumber
endfunction

function Network_sendMetadata takes integer this_2 returns nothing
	local integer clVar
	local integer clVar_2
	local integer intCount
	local integer realCount
	local integer booleanCount
	local integer asciiIntCount
	local integer maxLength
	local integer syncRounds
	local integer temp
	local integer temp_2
	local integer tuple_temp
	local integer tuple_temp_2
	local integer tuple_temp_3
	local integer tuple_temp_4
	local integer tuple_temp_5
	if SafetyChecks_SAFETY_CHECKS_ENABLED and Network_currentState[this_2] != 0 then
		call error("Network: trying to send duplicate metadata")
	endif
	if  not player_isIngame(Network_sender[this_2]) then
		call dispatch_Network_Network_Network_abort(this_2)
		return
	endif
	if Player_localPlayer == Network_sender[this_2] then
		set temp = NetworkConfig_DATA_PER_EXECUTE
		set clVar = alloc_LimitedExecuteCondition_executeWhile_Network_Network()
		set this_813[clVar] = this_2
		set temp_2 = clVar
		set clVar_2 = alloc_LimitedExecuteAction_executeWhile_Network_Network()
		set this_814[clVar_2] = this_2
		call executeWhile(temp, temp_2, clVar_2)
		call dispatch_StringEncoder_StringEncoder_StringEncoder_encode(Network_stringEncoder[this_2])
	endif
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_setMode(Network_dataBuffer[this_2], 3)
	set intCount = dispatch_HashBuffer_HashBuffer_HashBuffer_getIntCount(Network_dataBuffer[this_2])
	set realCount = dispatch_HashBuffer_HashBuffer_HashBuffer_getRealCount(Network_dataBuffer[this_2])
	set booleanCount = dispatch_HashBuffer_HashBuffer_HashBuffer_getBooleanCount(Network_dataBuffer[this_2])
	set asciiIntCount = dispatch_StringEncoder_StringEncoder_StringEncoder_getIntCount(Network_stringEncoder[this_2])
	set maxLength = max_4(intCount, realCount, booleanCount, asciiIntCount)
	set syncRounds = maxLength / GamecacheKeys_count + 1
	if Player_localPlayer == Network_sender[this_2] then
		set tuple_temp = intCount
		set tuple_temp_2 = realCount
		set tuple_temp_3 = booleanCount
		set tuple_temp_4 = asciiIntCount
		set tuple_temp_5 = syncRounds
		set Network_meta_ints[this_2] = tuple_temp
		set Network_meta_reals[this_2] = tuple_temp_2
		set Network_meta_booleans[this_2] = tuple_temp_3
		set Network_meta_asciiInts[this_2] = tuple_temp_4
		set Network_meta_syncs[this_2] = tuple_temp_5
		set Network_metaReceived[this_2] = true
		call dispatch_MetadataStore_Metadata_MetadataStore_setCounts(Network_metadataStore[this_2], Network_meta_ints[this_2], Network_meta_reals[this_2], Network_meta_booleans[this_2], Network_meta_asciiInts[this_2], Network_meta_syncs[this_2])
		call dispatch_MetadataStore_Metadata_MetadataStore_sync(Network_metadataStore[this_2])
	endif
	set Network_currentState[this_2] = 1
	call dispatch_Network_Network_Network_sendRound(this_2)
endfunction

function dispatch_Network_Network_Network_sendMetadata takes integer this_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.sendMetadata")
		else
			call error("Called Network.sendMetadata on invalid object.")
		endif
	endif
	call Network_sendMetadata(this_2)
endfunction

function Network_start takes integer this_2, integer callback_2 returns nothing
	set Network_finishCallback[this_2] = callback_2
	call dispatch_Network_Network_Network_sendMetadata(this_2)
endfunction

function dispatch_Network_Network_Network_start takes integer this_2, integer callback_2 returns nothing
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.start")
		else
			call error("Called Network.start on invalid object.")
		endif
	endif
	call Network_start(this_2, callback_2)
endfunction

function onSynchronized_onSynced_PersistentStore_Persistable takes integer this_2 returns nothing
	local integer clVar
	local integer temp
	call dispatch_FileReader_destroyFileReader(reader_663[this_2])
	set temp = network_657[this_2]
	set clVar = alloc_NetworkFinishedCallback_start_onSynced_PersistentStore_Persistable()
	set this_797[clVar] = this_796[this_2]
	set callback_618[clVar] = callback[this_2]
	call dispatch_Network_Network_Network_start(temp, clVar)
endfunction

function FileWriter_onDestroy takes integer this_2 returns nothing
	call AbstractFile_onDestroy(this_2)
endfunction

function dealloc_FileWriter takes integer obj returns nothing
	if AbstractFile_typeId[obj] == 0 then
		call error("Double free: object of type FileWriter")
	else
		set AbstractFile_firstFree = AbstractFile_firstFree + 1
		set AbstractFile_typeId[obj] = 0
	endif
endfunction

function destroyFileWriter takes integer this_2 returns nothing
	call FileWriter_onDestroy(this_2)
	call dealloc_FileWriter(this_2)
endfunction

function dispatch_FileWriter_destroyFileWriter takes integer this_2 returns nothing
	if AbstractFile_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling FileWriter.FileWriter")
		else
			call error("Called FileWriter.FileWriter on invalid object.")
		endif
	endif
	call destroyFileWriter(this_2)
endfunction

function dispatch_PersistableSaveCallback_Persistable_PersistableSaveCallback_onSaved takes integer this_2 returns nothing
endfunction

function Persistable_onSaved takes integer this_2 returns nothing
endfunction

function dispatch_Persistable_Persistable_Persistable_onSaved takes integer this_2 returns nothing
	if BufferSerializable_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Persistable.onSaved")
		else
			call error("Called Persistable.onSaved on invalid object.")
		endif
	endif
	call Persistable_onSaved(this_2)
endfunction

function onSynchronized_onSynced_PersistentStore_Persistable_1211 takes integer this_2 returns nothing
	if Player_localPlayer == PersistentStore_owner[this_800[this_2]] then
		call dispatch_FileWriter_destroyFileWriter(writer[this_2])
	endif
	if callback_619[this_2] != 0 then
		call dispatch_PersistableSaveCallback_Persistable_PersistableSaveCallback_onSaved(callback_619[this_2])
	endif
	call dispatch_Persistable_Persistable_Persistable_onSaved(PersistentStore_entity[this_800[this_2]])
endfunction

function dispatch_SynchronizationCallback_SyncSimple_SynchronizationCallback_onSynchronized takes integer this_2 returns nothing
	if SynchronizationCallback_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling SynchronizationCallback.onSynchronized")
		else
			call error("Called SynchronizationCallback.onSynchronized on invalid object.")
		endif
	endif
	if SynchronizationCallback_typeId[this_2] <= 849 then
		if SynchronizationCallback_typeId[this_2] <= 848 then
			call onSynchronized_onSynced_Network_Network(this_2)
		else
			call onSynchronized_onSynced_PersistentStore_Persistable(this_2)
		endif
	else
		call onSynchronized_onSynced_PersistentStore_Persistable_1211(this_2)
	endif
endfunction

function player_getId takes player this_2 returns integer
	return GetPlayerId(this_2)
endfunction

function SimpleSynchronizer_onPlayerFinishedSync takes integer this_2, player who returns nothing
	local integer tuple_temp = bitset_add(SimpleSynchronizer_syncedPlayers_val[this_2], player_getId(who))
	set SimpleSynchronizer_syncedPlayers_val[this_2] = tuple_temp
	if dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_areAllPlayersSynced(this_2) then
		call dispatch_SynchronizationCallback_SyncSimple_SynchronizationCallback_onSynchronized(SimpleSynchronizer_callback[this_2])
		call dispatch_SimpleSynchronizer_destroySimpleSynchronizer(this_2)
	endif
endfunction

function dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_onPlayerFinishedSync takes integer this_2, player who returns nothing
	if SimpleSynchronizer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling SimpleSynchronizer.onPlayerFinishedSync")
		else
			call error("Called SimpleSynchronizer.onPlayerFinishedSync on invalid object.")
		endif
	endif
	call SimpleSynchronizer_onPlayerFinishedSync(this_2, who)
endfunction

function onEvent_add_SyncSimple takes integer this_2 returns nothing
	local integer synchronizer_2 = SimpleSynchronizer_getSynchronizer(GetTriggerUnit())
	if synchronizer_2 != 0 then
		call dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_onPlayerFinishedSync(synchronizer_2, GetTriggerPlayer())
	endif
endfunction

function Iterator_onDestroy_250 takes integer this_2 returns nothing
endfunction

function dealloc_Iterator_685 takes integer obj returns nothing
	if Iterator_typeId_283[obj] == 0 then
		call error("Double free: object of type Iterator")
	else
		set Iterator_nextFree_280[Iterator_firstFree_272] = obj
		set Iterator_firstFree_272 = Iterator_firstFree_272 + 1
		set Iterator_typeId_283[obj] = 0
	endif
endfunction

function destroyIterator_730 takes integer this_2 returns nothing
	call Iterator_onDestroy_250(this_2)
	call dealloc_Iterator_685(this_2)
endfunction

function dispatch_Iterator_destroyIterator_875 takes integer this_2 returns nothing
	if Iterator_typeId_283[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Iterator.Iterator")
		else
			call error("Called Iterator.Iterator on invalid object.")
		endif
	endif
	call destroyIterator_730(this_2)
endfunction

function Iterator_close_242 takes integer this_2 returns nothing
	if Iterator_destroyOnClose_268[this_2] then
		call dispatch_Iterator_destroyIterator_875(this_2)
	endif
endfunction

function Iterator_hasNext_245 takes integer this_2 returns boolean
	return Iterator_current_266[this_2] != 0
endfunction

function Iterator_next_248 takes integer this_2 returns integer
	local integer res = Iterator_current_266[this_2]
	set Iterator_current_266[this_2] = SimpleSynchronizer_LinkedListModule_next[Iterator_current_266[this_2]]
	return res
endfunction

function alloc_Iterator_504 takes nothing returns integer
	local integer this_2
	if Iterator_firstFree_272 == 0 then
		if Iterator_maxIndex_276 < 32768 then
			set Iterator_maxIndex_276 = Iterator_maxIndex_276 + 1
			set this_2 = Iterator_maxIndex_276
			set Iterator_typeId_283[this_2] = 742
		else
			call error("Out of memory: Could not create Iterator.")
			set this_2 = 0
		endif
	else
		set Iterator_firstFree_272 = Iterator_firstFree_272 - 1
		set this_2 = Iterator_nextFree_280[Iterator_firstFree_272]
		set Iterator_typeId_283[this_2] = 742
	endif
	return this_2
endfunction

function construct_Iterator_642 takes integer this_2, boolean destroyOnClose returns nothing
	set Iterator_current_266[this_2] = SimpleSynchronizer_LinkedListModule_first
	set Iterator_destroyOnClose_268[this_2] = destroyOnClose
endfunction

function new_Iterator_1190 takes boolean destroyOnClose returns integer
	local integer this_2 = alloc_Iterator_504()
	call construct_Iterator_642(this_2, destroyOnClose)
	return this_2
endfunction

function SimpleSynchronizer_LinkedListModule_iterator takes nothing returns integer
	return new_Iterator_1190(true)
endfunction

function SimpleSynchronizer_onPlayerLeave takes integer this_2, player who returns nothing
	call dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_onPlayerFinishedSync(this_2, who)
endfunction

function dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_onPlayerLeave takes integer this_2, player who returns nothing
	if SimpleSynchronizer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling SimpleSynchronizer.onPlayerLeave")
		else
			call error("Called SimpleSynchronizer.onPlayerLeave on invalid object.")
		endif
	endif
	call SimpleSynchronizer_onPlayerLeave(this_2, who)
endfunction

function onEvent_add_SyncSimple_1205 takes integer this_2 returns nothing
	local player who = GetTriggerPlayer()
	local integer iterator = SimpleSynchronizer_LinkedListModule_iterator()
	local integer synchronizer_2
	loop
		exitwhen  not Iterator_hasNext_245(iterator)
		set synchronizer_2 = Iterator_next_248(iterator)
		call dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_onPlayerLeave(synchronizer_2, who)
	endloop
	call Iterator_close_242(iterator)
	set who = null
endfunction

function dispatch_EventListener_ClosureEvents_EventListener_onEvent takes integer this_2 returns nothing
	if EventListener_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling EventListener.onEvent")
		else
			call error("Called EventListener.onEvent on invalid object.")
		endif
	endif
	if EventListener_typeId[this_2] <= 680 then
		if EventListener_typeId[this_2] <= 679 then
			call onEvent_add_DamageEvent(this_2)
		else
			call onEvent_add_SyncSimple(this_2)
		endif
	else
		call onEvent_add_SyncSimple_1205(this_2)
	endif
endfunction

function handle_getHandleId takes handle this_2 returns integer
	return GetHandleId(this_2)
endfunction

function Log_debug takes string msg returns nothing
	call printLog(Player_localPlayer, 1, msg)
endfunction

function eventid_isKeyboardEvent takes eventid this_2 returns boolean
	local integer eventId = handle_getHandleId(this_2)
	return (eventId >= 261 and eventId <= 268) or eventId == 17
endfunction

function eventid_isMouseEvent takes eventid this_2 returns boolean
	local integer eventId = handle_getHandleId(this_2)
	return eventId >= 305 and eventId <= 307
endfunction

function eventid_isPlayerunitEvent takes eventid this_2 returns boolean
	local integer eventId = handle_getHandleId(this_2)
	return (eventId >= 18 and eventId <= 51) or (eventId >= 269 and eventId <= 277)
endfunction

function trigger_addAction takes trigger this_2, code actionFunc returns triggeraction
	return TriggerAddAction(this_2, actionFunc)
endfunction

function trigger_addCondition takes trigger this_2, boolexpr condition_2 returns triggercondition
	return TriggerAddCondition(this_2, condition_2)
endfunction

function trigger_registerPlayerUnitEvent takes trigger this_2, player whichPlayer, playerunitevent whichPlayerUnitEvent, boolexpr filter returns event
	return TriggerRegisterPlayerUnitEvent(this_2, whichPlayer, whichPlayerUnitEvent, filter)
endfunction

function registerPlayerUnitEvent_1254 takes playerunitevent p, code filter, code condition_2, code action_2 returns nothing
	local integer hid = handle_getHandleId(p)
	local integer k
	local filterfunc cond_result
	local trigger temp
	local player temp_2
	local playerunitevent temp_3
	if RegisterEvents_t[hid] == null then
		set RegisterEvents_t[hid] = CreateTrigger()
		set k = bj_MAX_PLAYER_SLOTS - 1
		loop
			exitwhen k < 0
			set temp = RegisterEvents_t[hid]
			set temp_2 = Player_players[k]
			set temp_3 = p
			if filter != null then
				set cond_result = Filter(filter)
			else
				set cond_result = null
			endif
			call trigger_registerPlayerUnitEvent(temp, temp_2, temp_3, cond_result)
			set k = k - 1
		endloop
	endif
	if condition_2 != null then
		call trigger_addCondition(RegisterEvents_t[hid], Filter(condition_2))
	endif
	if action_2 != null then
		call trigger_addAction(RegisterEvents_t[hid], action_2)
	endif
	set cond_result = null
	set temp = null
	set temp_2 = null
	set temp_3 = null
endfunction

function registerPlayerUnitEvent takes playerunitevent p, code c returns nothing
	call registerPlayerUnitEvent_1254(p, null, c, null)
endfunction

function trigger_registerPlayerEvent takes trigger this_2, player whichPlayer, playerevent whichPlayerEvent returns event
	return TriggerRegisterPlayerEvent(this_2, whichPlayer, whichPlayerEvent)
endfunction

function registerEventId takes eventid evnt returns integer
	local integer eventId = handle_getHandleId(evnt)
	local integer i_2
	local integer temp
	local trigger receiver
	local trigger receiver_2
	local trigger receiver_3
	set ClosureEvents_eventTypeCounter = ClosureEvents_eventTypeCounter + 1
	set ClosureEvents_eventidToIndex[eventId] = ClosureEvents_eventTypeCounter
	if eventid_isPlayerunitEvent(evnt) then
		call Log_debug("reg handleid: " + int_toString(eventId) + " -> eventId: " + int_toString(ClosureEvents_eventTypeCounter))
		call registerPlayerUnitEvent(ConvertPlayerUnitEvent(eventId), ref_function_EventListener_generalEventCallback)
	elseif evnt != EVENT_UNIT_DAMAGED and evnt != EVENT_PLAYER_LEAVE and evnt != ClosureEvents_EVENT_PLAYER_CHAT_FILTER and ( not eventid_isKeyboardEvent(evnt)) and ( not eventid_isMouseEvent(evnt)) then
		call error("registering handleid: " + int_toString(eventId) + " non-playerunitevent. Except EVENT_UNIT_DAMAGED and EVENT_PLAYER_LEAVE these are not supported right now.")
	endif
	if eventid_isMouseEvent(evnt) and ( not EventListener_useMouseEvents) then
		set EventListener_useMouseEvents = true
		set i_2 = 0
		set temp = bj_MAX_PLAYERS - 1
		loop
			exitwhen i_2 > temp
			set receiver = ClosureEvents_keyTrig
			call trigger_registerPlayerEvent(receiver, Player_players[i_2], EVENT_PLAYER_MOUSE_UP)
			set receiver_2 = receiver
			call trigger_registerPlayerEvent(receiver_2, Player_players[i_2], EVENT_PLAYER_MOUSE_DOWN)
			set receiver_3 = receiver_2
			call trigger_registerPlayerEvent(receiver_3, Player_players[i_2], EVENT_PLAYER_MOUSE_MOVE)
			set i_2 = i_2 + 1
		endloop
	endif
	set receiver = null
	set receiver_2 = null
	set receiver_3 = null
	return ClosureEvents_eventTypeCounter
endfunction

function eventid_toIntId takes eventid this_2 returns integer
	local integer id_2 = ClosureEvents_eventidToIndex[handle_getHandleId(this_2)]
	if id_2 == 0 then
		set id_2 = registerEventId(this_2)
	endif
	return id_2
endfunction

function fireEvents takes unit w_u returns boolean
	return true
endfunction

function EventListener_generalEventCallback takes nothing returns nothing
	local unit trigUnit = GetTriggerUnit()
	local integer id_2 = eventid_toIntId(GetTriggerEventId())
	local integer listener
	local integer listener_2
	if trigUnit != null and fireEvents(trigUnit) and unit_getIndex(trigUnit) > 0 then
		if EventListener_unitListenersFirsts[unit_getIndex(trigUnit)] != 0 then
			set listener = EventListener_unitListenersFirsts[unit_getIndex(trigUnit)]
			loop
				exitwhen  not (listener != 0)
				if EventListener_eventId[listener] == id_2 then
					call dispatch_EventListener_ClosureEvents_EventListener_onEvent(listener)
				endif
				set listener = EventListener_next[listener]
			endloop
		endif
	endif
	if EventListener_generalListenersFirsts[id_2] != 0 then
		set listener_2 = EventListener_generalListenersFirsts[id_2]
		loop
			exitwhen  not (listener_2 != 0)
			call dispatch_EventListener_ClosureEvents_EventListener_onEvent(listener_2)
			set listener_2 = EventListener_next[listener_2]
		endloop
	endif
	set trigUnit = null
endfunction

function alloc_LLEntry takes nothing returns integer
	local integer this_2
	if LLEntry_firstFree == 0 then
		if LLEntry_maxIndex < 32768 then
			set LLEntry_maxIndex = LLEntry_maxIndex + 1
			set this_2 = LLEntry_maxIndex
			set LLEntry_typeId[this_2] = 745
		else
			call error("Out of memory: Could not create LLEntry.")
			set this_2 = 0
		endif
	else
		set LLEntry_firstFree = LLEntry_firstFree - 1
		set this_2 = LLEntry_nextFree[LLEntry_firstFree]
		set LLEntry_typeId[this_2] = 745
	endif
	return this_2
endfunction

function construct_LLEntry takes integer this_2, integer elem, integer prev, integer next returns nothing
	set LLEntry_elem[this_2] = elem
	set LLEntry_prev[this_2] = prev
	set LLEntry_next[this_2] = next
endfunction

function new_LLEntry takes integer elem, integer prev, integer next returns integer
	local integer this_2 = alloc_LLEntry()
	call construct_LLEntry(this_2, elem, prev, next)
	return this_2
endfunction

function LinkedList_add_1 takes integer this_2, integer elems_0 returns nothing
	local integer entry = new_LLEntry(elems_0, LLEntry_prev[LinkedList_dummy[this_2]], LinkedList_dummy[this_2])
	set LLEntry_next[LLEntry_prev[LinkedList_dummy[this_2]]] = entry
	set LLEntry_prev[LinkedList_dummy[this_2]] = entry
	set LinkedList_size[this_2] = LinkedList_size[this_2] + 1
endfunction

function dispatch_LinkedList_LinkedList_LinkedList_add_1 takes integer this_2, integer elems_0 returns nothing
	if LinkedList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LinkedList.add")
		else
			call error("Called LinkedList.add on invalid object.")
		endif
	endif
	call LinkedList_add_1(this_2, elems_0)
endfunction

function LinkedList_push takes integer this_2, integer elem returns nothing
	call dispatch_LinkedList_LinkedList_LinkedList_add_1(this_2, elem)
endfunction

function dispatch_LinkedList_LinkedList_LinkedList_push takes integer this_2, integer elem returns nothing
	if LinkedList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LinkedList.push")
		else
			call error("Called LinkedList.push on invalid object.")
		endif
	endif
	call LinkedList_push(this_2, elem)
endfunction

function Sim3DSound_onDestroy takes integer this_2 returns nothing
endfunction

function dealloc_Sim3DSound takes integer obj returns nothing
	if Sim3DSound_typeId[obj] == 0 then
		call error("Double free: object of type Sim3DSound")
	else
		set Sim3DSound_firstFree = Sim3DSound_firstFree + 1
		set Sim3DSound_typeId[obj] = 0
	endif
endfunction

function destroySim3DSound takes integer this_2 returns nothing
	call Sim3DSound_onDestroy(this_2)
	call dealloc_Sim3DSound(this_2)
endfunction

function dispatch_Sim3DSound_destroySim3DSound takes integer this_2 returns nothing
	if Sim3DSound_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Sim3DSound.Sim3DSound")
		else
			call error("Called Sim3DSound.Sim3DSound on invalid object.")
		endif
	endif
	call destroySim3DSound(this_2)
endfunction

function SoundInstance_onDestroy takes integer this_2 returns nothing
endfunction

function dealloc_SoundInstance takes integer obj returns nothing
	if SoundInstance_typeId[obj] == 0 then
		call error("Double free: object of type SoundInstance")
	else
		set SoundInstance_firstFree = SoundInstance_firstFree + 1
		set SoundInstance_typeId[obj] = 0
	endif
endfunction

function destroySoundInstance takes integer this_2 returns nothing
	call SoundInstance_onDestroy(this_2)
	call dealloc_SoundInstance(this_2)
endfunction

function dispatch_SoundInstance_destroySoundInstance takes integer this_2 returns nothing
	if SoundInstance_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling SoundInstance.SoundInstance")
		else
			call error("Called SoundInstance.SoundInstance on invalid object.")
		endif
	endif
	call destroySoundInstance(this_2)
endfunction

function Table_loadInt takes integer this_2, integer parentKey returns integer
	return hashtable_loadInt(Table_ht, this_2, parentKey)
endfunction

function dispatch_Table_Table_Table_loadInt takes integer this_2, integer parentKey returns integer
	local integer Table_Table_loadInt_result
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.loadInt")
		else
			call error("Called Table.loadInt on invalid object.")
		endif
	endif
	set Table_Table_loadInt_result = Table_loadInt(this_2, parentKey)
	return Table_Table_loadInt_result
endfunction

function timer_getHandleId takes timer this_2 returns integer
	return GetHandleId(this_2)
endfunction

function timer_getData takes timer this_2 returns integer
	return dispatch_Table_Table_Table_loadInt(TimerUtils_timerData, timer_getHandleId(this_2))
endfunction

function timer_pause takes timer this_2 returns nothing
	call PauseTimer(this_2)
endfunction

function Table_saveInt takes integer this_2, integer parentKey, integer value_2 returns nothing
	call hashtable_saveInt(Table_ht, this_2, parentKey, value_2)
endfunction

function dispatch_Table_Table_Table_saveInt takes integer this_2, integer parentKey, integer value_2 returns nothing
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.saveInt")
		else
			call error("Called Table.saveInt on invalid object.")
		endif
	endif
	call Table_saveInt(this_2, parentKey, value_2)
endfunction

function timer_setData takes timer this_2, integer data returns nothing
	call dispatch_Table_Table_Table_saveInt(TimerUtils_timerData, timer_getHandleId(this_2), data)
endfunction

function timer_release takes timer this_2 returns nothing
	if this_2 == null then
		call error("Trying to release a null timer")
		return
	endif
	if timer_getData(this_2) == TimerUtils_HELD then
		call error("ReleaseTimer: Double free!")
		return
	endif
	call timer_setData(this_2, TimerUtils_HELD)
	call timer_pause(this_2)
	set TimerUtils_freeTimers[TimerUtils_freeTimersCount] = this_2
	set TimerUtils_freeTimersCount = TimerUtils_freeTimersCount + 1
endfunction

function SoundDefinition_recycle takes nothing returns nothing
	local timer tm = GetExpiredTimer()
	local integer sdata = timer_getData(tm)
	set SoundInstance_p[sdata] = null
	if SoundInstance_s3s[sdata] != 0 then
		call dispatch_Sim3DSound_destroySim3DSound(SoundInstance_s3s[sdata])
	endif
	if dispatch_LinkedList_LinkedList_LinkedList_size(SoundDefinition_soundStack[SoundInstance_soundDef[sdata]]) < 4 then
		call dispatch_LinkedList_LinkedList_LinkedList_push(SoundDefinition_soundStack[SoundInstance_soundDef[sdata]], sdata)
	else
		call dispatch_SoundInstance_destroySoundInstance(sdata)
	endif
	call timer_release(tm)
	set tm = null
endfunction

function Iterator_onDestroy takes integer this_2 returns nothing
endfunction

function dealloc_Iterator takes integer obj returns nothing
	if Iterator_typeId[obj] == 0 then
		call error("Double free: object of type Iterator")
	else
		set Iterator_nextFree[Iterator_firstFree] = obj
		set Iterator_firstFree = Iterator_firstFree + 1
		set Iterator_typeId[obj] = 0
	endif
endfunction

function destroyIterator takes integer this_2 returns nothing
	call Iterator_onDestroy(this_2)
	call dealloc_Iterator(this_2)
endfunction

function dispatch_Iterator_destroyIterator takes integer this_2 returns nothing
	if Iterator_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Iterator.Iterator")
		else
			call error("Called Iterator.Iterator on invalid object.")
		endif
	endif
	call destroyIterator(this_2)
endfunction

function Iterator_close takes integer this_2 returns nothing
	if Iterator_destroyOnClose[this_2] then
		call dispatch_Iterator_destroyIterator(this_2)
	endif
endfunction

function Iterator_hasNext takes integer this_2 returns boolean
	return Iterator_current[this_2] != 0
endfunction

function Iterator_next takes integer this_2 returns integer
	local integer res = Iterator_current[this_2]
	set Iterator_current[this_2] = TimedIOTaskExecutor_LinkedListModule_next[Iterator_current[this_2]]
	return res
endfunction

function alloc_Iterator takes nothing returns integer
	local integer this_2
	if Iterator_firstFree == 0 then
		if Iterator_maxIndex < 32768 then
			set Iterator_maxIndex = Iterator_maxIndex + 1
			set this_2 = Iterator_maxIndex
			set Iterator_typeId[this_2] = 739
		else
			call error("Out of memory: Could not create Iterator.")
			set this_2 = 0
		endif
	else
		set Iterator_firstFree = Iterator_firstFree - 1
		set this_2 = Iterator_nextFree[Iterator_firstFree]
		set Iterator_typeId[this_2] = 739
	endif
	return this_2
endfunction

function construct_Iterator takes integer this_2, boolean destroyOnClose returns nothing
	set Iterator_current[this_2] = TimedIOTaskExecutor_LinkedListModule_first
	set Iterator_destroyOnClose[this_2] = destroyOnClose
endfunction

function new_Iterator takes boolean destroyOnClose returns integer
	local integer this_2 = alloc_Iterator()
	call construct_Iterator(this_2, destroyOnClose)
	return this_2
endfunction

function TimedIOTaskExecutor_LinkedListModule_iterator takes nothing returns integer
	return new_Iterator(true)
endfunction

function TimedIOTaskExecutor_onStop takes nothing returns nothing
	if TimedIOTaskExecutor_timerStarted and TimedIOTaskExecutor_runningCount == 0 then
		call timer_pause(TimedIOTaskExecutor_updater)
	endif
endfunction

function alloc_ForForceCallback_execute_AbstractIOTaskExecutor_IOTaskExecutor takes nothing returns integer
	local integer this_2
	if ForForceCallback_firstFree == 0 then
		if ForForceCallback_maxIndex < 32768 then
			set ForForceCallback_maxIndex = ForForceCallback_maxIndex + 1
			set this_2 = ForForceCallback_maxIndex
			set ForForceCallback_typeId[this_2] = 693
		else
			call error("Out of memory: Could not create ForForceCallback_execute_AbstractIOTaskExecutor_IOTaskExecutor.")
			set this_2 = 0
		endif
	else
		set ForForceCallback_firstFree = ForForceCallback_firstFree - 1
		set this_2 = ForForceCallback_nextFree[ForForceCallback_firstFree]
		set ForForceCallback_typeId[this_2] = 693
	endif
	return this_2
endfunction

function alloc_ForForceCallback_execute_AbstractIOTaskExecutor_IOTaskExecutor_485 takes nothing returns integer
	local integer this_2
	if ForForceCallback_firstFree == 0 then
		if ForForceCallback_maxIndex < 32768 then
			set ForForceCallback_maxIndex = ForForceCallback_maxIndex + 1
			set this_2 = ForForceCallback_maxIndex
			set ForForceCallback_typeId[this_2] = 694
		else
			call error("Out of memory: Could not create ForForceCallback_execute_AbstractIOTaskExecutor_IOTaskExecutor.")
			set this_2 = 0
		endif
	else
		set ForForceCallback_firstFree = ForForceCallback_firstFree - 1
		set this_2 = ForForceCallback_nextFree[ForForceCallback_firstFree]
		set ForForceCallback_typeId[this_2] = 694
	endif
	return this_2
endfunction

function AbstractIOTaskExecutor_executeNext takes integer this_2 returns nothing
	local integer clVar
	local integer clVar_2
	if AbstractIOTaskExecutor_finished[this_2] then
		call error("AbstractIOTaskExecutor: trying to execute in a finished executor")
	endif
	if  not dispatch_LinkedList_LinkedList_LinkedList_isEmpty(AbstractIOTaskExecutor_taskQueue[this_2]) then
		set clVar = alloc_ForForceCallback_execute_AbstractIOTaskExecutor_IOTaskExecutor()
		set this_801[clVar] = this_2
		call execute(clVar)
	else
		if AbstractIOTaskExecutor_onCompleteTask[this_2] != 0 then
			set clVar_2 = alloc_ForForceCallback_execute_AbstractIOTaskExecutor_IOTaskExecutor_485()
			set this_802[clVar_2] = this_2
			call execute(clVar_2)
			call dispatch_IOTask_destroyIOTask(AbstractIOTaskExecutor_onCompleteTask[this_2])
			set AbstractIOTaskExecutor_onCompleteTask[this_2] = 0
		endif
		set AbstractIOTaskExecutor_finished[this_2] = true
	endif
endfunction

function dispatch_AbstractIOTaskExecutor_IOTaskExecutor_AbstractIOTaskExecutor_executeNext takes integer this_2 returns nothing
	if IOTaskExecutor_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractIOTaskExecutor.executeNext")
		else
			call error("Called AbstractIOTaskExecutor.executeNext on invalid object.")
		endif
	endif
	call AbstractIOTaskExecutor_executeNext(this_2)
endfunction

function TimedIOTaskExecutor_update takes integer this_2 returns nothing
	if ( not AbstractIOTaskExecutor_finished[this_2]) and TimedIOTaskExecutor_lastExecution[this_2] + TimedIOTaskExecutor_delay[this_2] <= GameTimer_currentTime then
		call dispatch_AbstractIOTaskExecutor_IOTaskExecutor_AbstractIOTaskExecutor_executeNext(this_2)
		set TimedIOTaskExecutor_lastExecution[this_2] = GameTimer_currentTime
		if AbstractIOTaskExecutor_finished[this_2] then
			set TimedIOTaskExecutor_runningCount = TimedIOTaskExecutor_runningCount - 1
			call TimedIOTaskExecutor_onStop()
		endif
	endif
endfunction

function dispatch_TimedIOTaskExecutor_IOTaskExecutor_TimedIOTaskExecutor_update takes integer this_2 returns nothing
	if IOTaskExecutor_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling TimedIOTaskExecutor.update")
		else
			call error("Called TimedIOTaskExecutor.update on invalid object.")
		endif
	endif
	call TimedIOTaskExecutor_update(this_2)
endfunction

function TimedIOTaskExecutor_updateInstances takes nothing returns nothing
	local integer iterator = TimedIOTaskExecutor_LinkedListModule_iterator()
	local integer executor
	loop
		exitwhen  not Iterator_hasNext(iterator)
		set executor = Iterator_next(iterator)
		call dispatch_TimedIOTaskExecutor_IOTaskExecutor_TimedIOTaskExecutor_update(executor)
	endloop
	call Iterator_close(iterator)
endfunction

function Log_trace takes string msg returns nothing
	call printLog(Player_localPlayer, 0, msg)
endfunction

function badOrderList takes integer id_2 returns boolean
	return id_2 == 852055 or id_2 == 852056 or id_2 == 852064 or id_2 == 852065 or id_2 == 852067 or id_2 == 852068 or id_2 == 852076 or id_2 == 852077 or id_2 == 852090 or id_2 == 852091 or id_2 == 852100 or id_2 == 852102 or id_2 == 852103 or id_2 == 852107 or id_2 == 852108 or id_2 == 852129 or id_2 == 852130 or id_2 == 852133 or id_2 == 852134 or id_2 == 852136 or id_2 == 852137 or id_2 == 852150 or id_2 == 852151 or id_2 == 852174 or id_2 == 852158 or id_2 == 852159 or id_2 == 852162 or id_2 == 852163 or id_2 == 852174 or id_2 == 852175 or id_2 == 852177 or id_2 == 852178 or id_2 == 852191 or id_2 == 852192 or id_2 == 852198 or id_2 == 852199 or id_2 == 852203 or id_2 == 852204 or id_2 == 852212 or id_2 == 852213 or id_2 == 852244 or id_2 == 852245 or id_2 == 852249 or id_2 == 852250 or id_2 == 852255 or id_2 == 852256 or id_2 == 852458 or id_2 == 852459 or id_2 == 852478 or id_2 == 852479 or id_2 == 852484 or id_2 == 852485 or id_2 == 852515 or id_2 == 852516 or id_2 == 852522 or id_2 == 852523 or id_2 == 852540 or id_2 == 852541 or id_2 == 852543 or id_2 == 852544 or id_2 == 852546 or id_2 == 852547 or id_2 == 852549 or id_2 == 852550 or id_2 == 852552 or id_2 == 852553 or id_2 == 852562 or id_2 == 852563 or id_2 == 852571 or id_2 == 852578 or id_2 == 852579 or id_2 == 852589 or id_2 == 852590 or id_2 == 852602 or id_2 == 852603 or id_2 == 852671 or id_2 == 852672
endfunction

function unit_orderFilter takes unit this_2, integer id_2 returns boolean
	return id_2 == 851971 or id_2 == 851986 or id_2 == 851983 or id_2 == 851984 or id_2 == 851990 or id_2 == 851993 or (id_2 >= 852055 and id_2 <= 852762) and ( not badOrderList(id_2))
endfunction

function conditions takes nothing returns boolean
	return unit_orderFilter(GetTriggerUnit(), GetIssuedOrderId())
endfunction

function getOrderPos takes nothing returns real
	set getOrderPos_return_x = GetOrderPointX()
	set getOrderPos_return_y = GetOrderPointY()
	return getOrderPos_return_x
endfunction

function widget_getX takes widget this_2 returns real
	return GetWidgetX(this_2)
endfunction

function widget_getY takes widget this_2 returns real
	return GetWidgetY(this_2)
endfunction

function widget_getPos takes widget this_2 returns real
	set widget_getPos_return_x = widget_getX(this_2)
	set widget_getPos_return_y = widget_getY(this_2)
	return widget_getPos_return_x
endfunction

function getOrderTargetPos takes nothing returns real
	set getOrderTargetPos_return_x = widget_getPos(GetOrderTarget())
	set getOrderTargetPos_return_y = widget_getPos_return_y
	return getOrderTargetPos_return_x
endfunction

function alloc_Order takes nothing returns integer
	local integer this_2
	if Order_firstFree == 0 then
		if Order_maxIndex < 32768 then
			set Order_maxIndex = Order_maxIndex + 1
			set this_2 = Order_maxIndex
			set Order_typeId[this_2] = 815
		else
			call error("Out of memory: Could not create Order.")
			set this_2 = 0
		endif
	else
		set Order_firstFree = Order_firstFree - 1
		set this_2 = Order_nextFree[Order_firstFree]
		set Order_typeId[this_2] = 815
	endif
	return this_2
endfunction

function dealloc_Order takes integer obj returns nothing
	if Order_typeId[obj] == 0 then
		call error("Double free: object of type Order")
	else
		set Order_nextFree[Order_firstFree] = obj
		set Order_firstFree = Order_firstFree + 1
		set Order_typeId[obj] = 0
	endif
endfunction

function cyc_destroyOrder takes integer funcChoice, integer this_2 returns nothing
	if funcChoice == 0 then
		call cyc_destroyOrder(2, this_2)
		call dealloc_Order(this_2)
	elseif funcChoice == 1 then
		if Order_typeId[this_2] == 0 then
			if this_2 == 0 then
				call error("Nullpointer exception when calling Order.Order")
			else
				call error("Called Order.Order on invalid object.")
			endif
		endif
		call cyc_destroyOrder(0, this_2)
	elseif funcChoice == 2 then
		if Order_prev[this_2] != 0 then
			call cyc_destroyOrder(1, Order_prev[this_2])
			set Order_prev[this_2] = 0
		endif
	endif
endfunction

function unit_getX takes unit this_2 returns real
	return GetUnitX(this_2)
endfunction

function unit_getY takes unit this_2 returns real
	return GetUnitY(this_2)
endfunction

function unit_getPos takes unit this_2 returns real
	set unit_getPos_return_x = unit_getX(this_2)
	set unit_getPos_return_y = unit_getY(this_2)
	return unit_getPos_return_x
endfunction

function construct_Order takes integer this_2, unit ordered, integer orderId, integer ordtype, widget target, real targetPos_x, real targetPos_y returns nothing
	local integer index
	local integer i_2
	local integer e
	local integer tmp
	local real tuple_temp
	local real tuple_temp_2
	local real tuple_temp_3
	local real tuple_temp_4
	set Order_prev[this_2] = 0
	set Order_orderedUnit[this_2] = null
	set Order_orderedUnit[this_2] = ordered
	set tuple_temp = unit_getPos(ordered)
	set tuple_temp_2 = unit_getPos_return_y
	set tuple_temp_3 = targetPos_x
	set tuple_temp_4 = targetPos_y
	set index = unit_getIndex(ordered)
	if LastOrder_lastOrder[index] != 0 then
		if Order_orderedUnit[LastOrder_lastOrder[index]] == Order_orderedUnit[this_2] then
			set Order_prev[this_2] = LastOrder_lastOrder[index]
		else
			call cyc_destroyOrder(1, LastOrder_lastOrder[index])
		endif
	endif
	set i_2 = 0
	set e = this_2
	loop
		exitwhen  not (Order_prev[e] != 0)
		set i_2 = i_2 + 1
		if i_2 > LastOrder_ORDERS_TO_HOLD then
			set tmp = Order_prev[e]
			call cyc_destroyOrder(1, tmp)
			set Order_prev[e] = 0
			exitwhen true
		endif
		set e = Order_prev[e]
	endloop
	set LastOrder_lastOrder[index] = this_2
endfunction

function new_Order takes unit ordered, integer orderId, integer ordtype, widget target, real targetPos_x, real targetPos_y returns integer
	local integer this_2 = alloc_Order()
	call construct_Order(this_2, ordered, orderId, ordtype, target, targetPos_x, targetPos_y)
	return this_2
endfunction

function actions takes nothing returns nothing
	local unit u
	local widget t
	local integer oid
	if conditions() then
		set u = GetTriggerUnit()
		set t = GetOrderTarget()
		set oid = GetIssuedOrderId()
		call Log_trace("ordered: <" + OrderId2StringBJ(oid) + ">")
		if GetTriggerEventId() == EVENT_PLAYER_UNIT_ISSUED_TARGET_ORDER then
			call new_Order(u, oid, 0, t, getOrderTargetPos(), getOrderTargetPos_return_y)
		elseif GetTriggerEventId() == EVENT_PLAYER_UNIT_ISSUED_POINT_ORDER then
			call new_Order(u, oid, 1, null, getOrderPos(), getOrderPos_return_y)
		elseif GetTriggerEventId() == EVENT_PLAYER_UNIT_ISSUED_ORDER then
			call new_Order(u, oid, 2, null, unit_getPos(u), unit_getPos_return_y)
		endif
	endif
	set u = null
	set t = null
endfunction

function destructable_getLife takes destructable this_2 returns real
	return GetDestructableLife(this_2)
endfunction

function destructable_kill takes destructable this_2 returns nothing
	call KillDestructable(this_2)
endfunction

function code__EnumDestructablesInRect_Knockback3_Knockback3 takes nothing returns nothing
	local destructable des = GetEnumDestructable()
	if destructable_getLife(des) > 0. then
		call destructable_kill(des)
		set Knockback3_hitDestructable = true
	endif
	set des = null
endfunction

function item_isVisible takes item this_2 returns boolean
	return IsItemVisible(this_2)
endfunction

function item_setVisible takes item this_2, boolean flag returns nothing
	call SetItemVisible(this_2, flag)
endfunction

function code__EnumItemsInRect_TerrainUtils takes nothing returns nothing
	if item_isVisible(GetEnumItem()) then
		set TerrainUtils_hiddenItems[TerrainUtils_hiddenItemsCount] = GetEnumItem()
		call item_setVisible(TerrainUtils_hiddenItems[TerrainUtils_hiddenItemsCount], false)
		set TerrainUtils_hiddenItemsCount = TerrainUtils_hiddenItemsCount + 1
	endif
endfunction

function currentCallback takes nothing returns integer
	return ClosureForGroups_tempCallbacks[ClosureForGroups_tempCallbacksCount - 1]
endfunction

function unitToIndex takes unit object returns integer
	return handle_getHandleId(object)
endfunction

function callback_forEachFrom_LinkedList takes integer this_2, unit u returns nothing
	call dispatch_LinkedList_LinkedList_LinkedList_add_1(result[this_2], unitToIndex(u))
endfunction

function callback_forEachFrom_Preloader takes integer this_2, unit u returns nothing
	call unit_remove(u)
endfunction

function callback_forUnitsSelected_SyncSimple takes integer this_2, unit u returns nothing
	set SyncSimple_count = SyncSimple_count + 1
	set SyncSimple_last = u
endfunction

function dispatch_ForGroupCallback_ClosureForGroups_ForGroupCallback_callback takes integer this_2, unit u returns nothing
	if ForGroupCallback_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling ForGroupCallback.callback")
		else
			call error("Called ForGroupCallback.callback on invalid object.")
		endif
	endif
	if ForGroupCallback_typeId[this_2] <= 709 then
		if ForGroupCallback_typeId[this_2] <= 708 then
			call callback_forEachFrom_LinkedList(this_2, u)
		else
			call callback_forEachFrom_Preloader(this_2, u)
		endif
	else
		call callback_forUnitsSelected_SyncSimple(this_2, u)
	endif
endfunction

function filterCallback takes unit filter returns nothing
	if ClosureForGroups_iterCount < ClosureForGroups_maxCount then
		call dispatch_ForGroupCallback_ClosureForGroups_ForGroupCallback_callback(currentCallback(), filter)
	endif
	set ClosureForGroups_iterCount = ClosureForGroups_iterCount + 1
endfunction

function code__Filter_ClosureForGroups takes nothing returns nothing
	call filterCallback(GetFilterUnit())
endfunction

function trigger_registerUnitEvent takes trigger this_2, unit whichUnit, unitevent whichEvent returns event
	return TriggerRegisterUnitEvent(this_2, whichUnit, whichEvent)
endfunction

function addUnit takes unit u returns nothing
	call trigger_registerUnitEvent(DamageDetection_current, u, EVENT_UNIT_DAMAGED)
endfunction

function code__Filter_DamageDetection takes nothing returns nothing
	call addUnit(GetFilterUnit())
endfunction

function popUnit takes nothing returns nothing
	set OnUnitEnterLeave_tempUnitsCount = OnUnitEnterLeave_tempUnitsCount - 1
endfunction

function pushUnit takes unit u returns nothing
	set OnUnitEnterLeave_tempUnits[OnUnitEnterLeave_tempUnitsCount] = u
	set OnUnitEnterLeave_tempUnitsCount = OnUnitEnterLeave_tempUnitsCount + 1
endfunction

function trigger_evaluate takes trigger this_2 returns boolean
	return TriggerEvaluate(this_2)
endfunction

function unit_makeAbilityPermanent takes unit this_2, integer abil, boolean flag returns boolean
	return UnitMakeAbilityPermanent(this_2, flag, abil)
endfunction

function prepareUnit takes unit u returns nothing
	local unit receiver = u
	local unit receiver_2
	call unit_addAbility(receiver, OnUnitEnterLeave_ABILITY_ID)
	set receiver_2 = receiver
	call unit_makeAbilityPermanent(receiver_2, OnUnitEnterLeave_ABILITY_ID, true)
	call pushUnit(u)
	call trigger_evaluate(OnUnitEnterLeave_eventTrigger)
	call popUnit()
	set receiver = null
	set receiver_2 = null
endfunction

function code__Filter_registerEnterRegion_nullTimer_OnUnitEnterLeave takes nothing returns nothing
	call prepareUnit(GetFilterUnit())
endfunction

function code__ForGroup_nullTimer_OnUnitEnterLeave takes nothing returns nothing
	call prepareUnit(GetEnumUnit())
endfunction

function code__addAction_nullTimer_ClosureEvents takes nothing returns nothing
	call EventListener_generalEventCallback()
endfunction

function code__addAction_nullTimer_ClosureEvents_607 takes nothing returns nothing
	call EventListener_generalEventCallback()
endfunction

function code__addAction_nullTimer_ClosureEvents_608 takes nothing returns nothing
	call EventListener_generalEventCallback()
endfunction

function getEnterLeaveUnit takes nothing returns unit
	return OnUnitEnterLeave_tempUnits[OnUnitEnterLeave_tempUnitsCount - 1]
endfunction

function code__onEnter_DamageDetection takes nothing returns nothing
	call addUnit(getEnterLeaveUnit())
endfunction

function code__onEnter_DamageEvent takes nothing returns nothing
	local unit receiver = getEnterLeaveUnit()
	local unit receiver_2
	call unit_addAbility(receiver, DamageEvent_DETECT_NATIVE_ABILITIES_ID)
	set receiver_2 = receiver
	call unit_makeAbilityPermanent(receiver_2, DamageEvent_DETECT_NATIVE_ABILITIES_ID, true)
	set receiver = null
	set receiver_2 = null
endfunction

function shouldIndex takes unit w_u returns boolean
	return true
endfunction

function alloc_UnitIndex takes nothing returns integer
	local integer this_2
	if UnitIndex_firstFree == 0 then
		if UnitIndex_maxIndex < 32768 then
			set UnitIndex_maxIndex = UnitIndex_maxIndex + 1
			set this_2 = UnitIndex_maxIndex
			set UnitIndex_typeId[this_2] = 867
		else
			call error("Out of memory: Could not create UnitIndex.")
			set this_2 = 0
		endif
	else
		set UnitIndex_firstFree = UnitIndex_firstFree - 1
		set this_2 = UnitIndex_nextFree[UnitIndex_firstFree]
		set UnitIndex_typeId[this_2] = 867
	endif
	return this_2
endfunction

function popUnit_1227 takes nothing returns nothing
	set UnitIndexer_tempUnitsCount = UnitIndexer_tempUnitsCount - 1
endfunction

function pushUnit_1236 takes unit u returns nothing
	set UnitIndexer_tempUnits[UnitIndexer_tempUnitsCount] = u
	set UnitIndexer_tempUnitsCount = UnitIndexer_tempUnitsCount + 1
endfunction

function unit_setUserData takes unit this_2, integer data returns nothing
	call SetUnitUserData(this_2, data)
endfunction

function construct_UnitIndex takes integer this_2, unit whichUnit returns nothing
	set UnitIndex__unit[this_2] = whichUnit
	call unit_setUserData(UnitIndex__unit[this_2], this_2)
	call pushUnit_1236(whichUnit)
	call trigger_evaluate(UnitIndexer_onIndexTrigger)
	call popUnit_1227()
endfunction

function new_UnitIndex takes unit whichUnit returns integer
	local integer this_2 = alloc_UnitIndex()
	call construct_UnitIndex(this_2, whichUnit)
	return this_2
endfunction

function unit_toUnitIndex takes unit this_2 returns integer
	local integer instance = unit_getUserData(this_2)
	if instance == 0 then
		set instance = new_UnitIndex(this_2)
	endif
	return instance
endfunction

function code__onEnter_UnitIndexer takes nothing returns nothing
	if shouldIndex(getEnterLeaveUnit()) then
		call unit_toUnitIndex(getEnterLeaveUnit())
	endif
endfunction

function code__onLeave_SyncSimple takes nothing returns nothing
	local integer synchronizer_2 = SimpleSynchronizer_getSynchronizer(getEnterLeaveUnit())
	if synchronizer_2 != 0 and ( not dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_areAllPlayersSynced(synchronizer_2)) then
		call Log_error("SimpleSynchronizer: dummy unit was removed before all players synced, make sure your map doesn't remove neutral dummy units")
	endif
endfunction

function UnitIndex_onDestroy takes integer this_2 returns nothing
	call pushUnit_1236(UnitIndex__unit[this_2])
	call trigger_evaluate(UnitIndexer_onDeindexTrigger)
	call popUnit_1227()
	call unit_setUserData(UnitIndex__unit[this_2], 0)
endfunction

function dealloc_UnitIndex takes integer obj returns nothing
	if UnitIndex_typeId[obj] == 0 then
		call error("Double free: object of type UnitIndex")
	else
		set UnitIndex_nextFree[UnitIndex_firstFree] = obj
		set UnitIndex_firstFree = UnitIndex_firstFree + 1
		set UnitIndex_typeId[obj] = 0
	endif
endfunction

function destroyUnitIndex takes integer this_2 returns nothing
	call UnitIndex_onDestroy(this_2)
	call dealloc_UnitIndex(this_2)
endfunction

function dispatch_UnitIndex_destroyUnitIndex takes integer this_2 returns nothing
	if UnitIndex_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling UnitIndex.UnitIndex")
		else
			call error("Called UnitIndex.UnitIndex on invalid object.")
		endif
	endif
	call destroyUnitIndex(this_2)
endfunction

function unit_deindex takes unit this_2 returns boolean
	if unit_getUserData(this_2) == 0 then
		return false
	else
		call dispatch_UnitIndex_destroyUnitIndex(unit_toUnitIndex(this_2))
		return true
	endif
endfunction

function code__onLeave_UnitIndexer takes nothing returns nothing
	if shouldIndex(getEnterLeaveUnit()) then
		call unit_deindex(getEnterLeaveUnit())
	endif
endfunction

function getIndexingUnit takes nothing returns unit
	return UnitIndexer_tempUnits[UnitIndexer_tempUnitsCount - 1]
endfunction

function HashMap_get takes integer this_2, integer key returns integer
	return dispatch_Table_Table_Table_loadInt(this_2, key)
endfunction

function dispatch_HashMap_HashMap_HashMap_get takes integer this_2, integer key returns integer
	local integer HashMap_HashMap_get_result
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashMap.get")
		else
			call error("Called HashMap.get on invalid object.")
		endif
	endif
	set HashMap_HashMap_get_result = HashMap_get(this_2, key)
	return HashMap_HashMap_get_result
endfunction

function Table_hasInt takes integer this_2, integer parentKey returns boolean
	return hashtable_hasInt(Table_ht, this_2, parentKey)
endfunction

function dispatch_Table_Table_Table_hasInt takes integer this_2, integer parentKey returns boolean
	local boolean Table_Table_hasInt_result
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.hasInt")
		else
			call error("Called Table.hasInt on invalid object.")
		endif
	endif
	set Table_Table_hasInt_result = Table_hasInt(this_2, parentKey)
	return Table_Table_hasInt_result
endfunction

function HashMap_has takes integer this_2, integer key returns boolean
	return dispatch_Table_Table_Table_hasInt(this_2, key)
endfunction

function dispatch_HashMap_HashMap_HashMap_has takes integer this_2, integer key returns boolean
	local boolean HashMap_HashMap_has_result
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashMap.has")
		else
			call error("Called HashMap.has on invalid object.")
		endif
	endif
	set HashMap_HashMap_has_result = HashMap_has(this_2, key)
	return HashMap_HashMap_has_result
endfunction

function Table_removeInt takes integer this_2, integer parentKey returns nothing
	call RemoveSavedInteger(Table_ht, this_2, parentKey)
endfunction

function dispatch_Table_Table_Table_removeInt takes integer this_2, integer parentKey returns nothing
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.removeInt")
		else
			call error("Called Table.removeInt on invalid object.")
		endif
	endif
	call Table_removeInt(this_2, parentKey)
endfunction

function HashMap_remove takes integer this_2, integer key returns nothing
	if dispatch_HashMap_HashMap_HashMap_has(this_2, key) then
		set HashMap_size[this_2] = HashMap_size[this_2] - 1
	endif
	call dispatch_Table_Table_Table_removeInt(this_2, key)
endfunction

function HashList_count takes integer this_2, integer elem returns integer
	return hashtable_loadInt(HashList_occurences, this_2, elem)
endfunction

function dispatch_HashList_HashList_HashList_count takes integer this_2, integer elem returns integer
	local integer HashList_HashList_count_result
	if HashList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashList.count")
		else
			call error("Called HashList.count on invalid object.")
		endif
	endif
	set HashList_HashList_count_result = HashList_count(this_2, elem)
	return HashList_HashList_count_result
endfunction

function HashList_decrOccurences takes integer this_2, integer elem returns nothing
	call hashtable_saveInt(HashList_occurences, this_2, elem, dispatch_HashList_HashList_HashList_count(this_2, elem) - 1)
endfunction

function dispatch_HashList_HashList_HashList_decrOccurences takes integer this_2, integer elem returns nothing
	if HashList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashList.decrOccurences")
		else
			call error("Called HashList.decrOccurences on invalid object.")
		endif
	endif
	call HashList_decrOccurences(this_2, elem)
endfunction

function HashList_removeAt takes integer this_2, integer index returns nothing
	local integer i_2
	local integer temp
	call dispatch_HashList_HashList_HashList_decrOccurences(this_2, hashtable_loadInt(HashList_ht, this_2, index))
	set i_2 = index
	set temp = HashList_size[this_2]
	loop
		exitwhen i_2 > temp
		call hashtable_saveInt(HashList_ht, this_2, i_2, hashtable_loadInt(HashList_ht, this_2, i_2 + 1))
		set i_2 = i_2 + 1
	endloop
	set HashList_size[this_2] = HashList_size[this_2] - 1
endfunction

function HashList_get takes integer this_2, integer index returns integer
	return hashtable_loadInt(HashList_ht, this_2, index)
endfunction

function dispatch_HashList_HashList_HashList_get takes integer this_2, integer index returns integer
	local integer HashList_HashList_get_result
	if HashList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashList.get")
		else
			call error("Called HashList.get on invalid object.")
		endif
	endif
	set HashList_HashList_get_result = HashList_get(this_2, index)
	return HashList_HashList_get_result
endfunction

function HashList_size_2 takes integer this_2 returns integer
	return HashList_size[this_2]
endfunction

function dispatch_HashList_HashList_HashList_size takes integer this_2 returns integer
	local integer HashList_HashList_size_result
	if HashList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashList.size")
		else
			call error("Called HashList.size on invalid object.")
		endif
	endif
	set HashList_HashList_size_result = HashList_size_2(this_2)
	return HashList_HashList_size_result
endfunction

function hashtable_removeInt takes hashtable this_2, integer parentKey, integer childKey returns nothing
	call RemoveSavedInteger(this_2, parentKey, childKey)
endfunction

function HashSet_removeAt takes integer this_2, integer index returns nothing
	local integer i_2
	local integer temp
	call hashtable_removeInt(HashSet_position, this_2, dispatch_HashList_HashList_HashList_get(this_2, index))
	set i_2 = index + 1
	set temp = dispatch_HashList_HashList_HashList_size(this_2)
	loop
		exitwhen i_2 > temp
		call hashtable_saveInt(HashSet_position, this_2, dispatch_HashList_HashList_HashList_get(this_2, i_2), hashtable_loadInt(HashSet_position, this_2, dispatch_HashList_HashList_HashList_get(this_2, i_2)) - 1)
		set i_2 = i_2 + 1
	endloop
	call HashList_removeAt(this_2, index)
endfunction

function dispatch_HashList_HashList_HashList_removeAt takes integer this_2, integer index returns nothing
	if HashList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashList.removeAt")
		else
			call error("Called HashList.removeAt on invalid object.")
		endif
	endif
	if HashList_typeId[this_2] <= 716 then
		call HashList_removeAt(this_2, index)
	else
		call HashSet_removeAt(this_2, index)
	endif
endfunction

function HashList_remove takes integer this_2, integer t returns nothing
	local integer i_2 = 0
	local integer temp = HashList_size[this_2] - 1
	loop
		exitwhen i_2 > temp
		if t == hashtable_loadInt(HashList_ht, this_2, i_2) then
			call dispatch_HashList_HashList_HashList_removeAt(this_2, i_2)
			exitwhen true
		endif
		set i_2 = i_2 + 1
	endloop
endfunction

function dispatch_HashSet_HashSet_HashSet_removeAt takes integer this_2, integer index returns nothing
	if HashList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashSet.removeAt")
		else
			call error("Called HashSet.removeAt on invalid object.")
		endif
	endif
	call HashSet_removeAt(this_2, index)
endfunction

function HashSet_remove takes integer this_2, integer elem returns nothing
	if dispatch_HashList_HashList_HashList_count(this_2, elem) > 0 then
		call dispatch_HashSet_HashSet_HashSet_removeAt(this_2, hashtable_loadInt(HashSet_position, this_2, elem))
	endif
endfunction

function dispatch_HashList_HashList_HashList_remove takes integer this_2, integer t returns nothing
	if HashList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashList.remove")
		else
			call error("Called HashList.remove on invalid object.")
		endif
	endif
	if HashList_typeId[this_2] <= 716 then
		call HashList_remove(this_2, t)
	else
		call HashSet_remove(this_2, t)
	endif
endfunction

function HashList_has takes integer this_2, integer elem returns boolean
	return dispatch_HashList_HashList_HashList_count(this_2, elem) > 0
endfunction

function dispatch_HashList_HashList_HashList_has takes integer this_2, integer elem returns boolean
	local boolean HashList_HashList_has_result
	if HashList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashList.has")
		else
			call error("Called HashList.has on invalid object.")
		endif
	endif
	set HashList_HashList_has_result = HashList_has(this_2, elem)
	return HashList_HashList_has_result
endfunction

function IterableMap_hasKey takes integer this_2, integer key returns boolean
	return dispatch_HashList_HashList_HashList_has(IterableMap_keys[this_2], key)
endfunction

function dispatch_IterableMap_HashMap_IterableMap_hasKey takes integer this_2, integer key returns boolean
	local boolean HashMap_IterableMap_hasKey_result
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling IterableMap.hasKey")
		else
			call error("Called IterableMap.hasKey on invalid object.")
		endif
	endif
	set HashMap_IterableMap_hasKey_result = IterableMap_hasKey(this_2, key)
	return HashMap_IterableMap_hasKey_result
endfunction

function IterableMap_remove takes integer this_2, integer key returns nothing
	call HashMap_remove(this_2, key)
	if dispatch_IterableMap_HashMap_IterableMap_hasKey(this_2, key) then
		call dispatch_HashList_HashList_HashList_remove(IterableMap_keys[this_2], key)
	endif
endfunction

function dispatch_HashMap_HashMap_HashMap_remove takes integer this_2, integer key returns nothing
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashMap.remove")
		else
			call error("Called HashMap.remove on invalid object.")
		endif
	endif
	if Table_typeId[this_2] <= 856 then
		call HashMap_remove(this_2, key)
	else
		call IterableMap_remove(this_2, key)
	endif
endfunction

function HashMap_put takes integer this_2, integer key, integer value_2 returns nothing
	if  not dispatch_HashMap_HashMap_HashMap_has(this_2, key) then
		set HashMap_size[this_2] = HashMap_size[this_2] + 1
	endif
	call dispatch_Table_Table_Table_saveInt(this_2, key, value_2)
endfunction

function HashList_incrOccurences takes integer this_2, integer elem returns nothing
	call hashtable_saveInt(HashList_occurences, this_2, elem, dispatch_HashList_HashList_HashList_count(this_2, elem) + 1)
endfunction

function dispatch_HashList_HashList_HashList_incrOccurences takes integer this_2, integer elem returns nothing
	if HashList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashList.incrOccurences")
		else
			call error("Called HashList.incrOccurences on invalid object.")
		endif
	endif
	call HashList_incrOccurences(this_2, elem)
endfunction

function HashList_add_1 takes integer this_2, integer elems_0 returns nothing
	call hashtable_saveInt(HashList_ht, this_2, HashList_size[this_2], elems_0)
	call dispatch_HashList_HashList_HashList_incrOccurences(this_2, elems_0)
	set HashList_size[this_2] = HashList_size[this_2] + 1
endfunction

function HashSet_add_1 takes integer this_2, integer elems_0 returns nothing
	if dispatch_HashList_HashList_HashList_count(this_2, elems_0) <= 0 then
		call hashtable_saveInt(HashSet_position, this_2, elems_0, dispatch_HashList_HashList_HashList_size(this_2))
		call HashList_add_1(this_2, elems_0)
	endif
endfunction

function dispatch_HashList_HashList_HashList_add_1 takes integer this_2, integer elems_0 returns nothing
	if HashList_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashList.add")
		else
			call error("Called HashList.add on invalid object.")
		endif
	endif
	if HashList_typeId[this_2] <= 716 then
		call HashList_add_1(this_2, elems_0)
	else
		call HashSet_add_1(this_2, elems_0)
	endif
endfunction

function IterableMap_put takes integer this_2, integer key, integer value_2 returns nothing
	call HashMap_put(this_2, key, value_2)
	if  not dispatch_IterableMap_HashMap_IterableMap_hasKey(this_2, key) then
		call dispatch_HashList_HashList_HashList_add_1(IterableMap_keys[this_2], key)
	endif
endfunction

function dispatch_HashMap_HashMap_HashMap_put takes integer this_2, integer key, integer value_2 returns nothing
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashMap.put")
		else
			call error("Called HashMap.put on invalid object.")
		endif
	endif
	if Table_typeId[this_2] <= 856 then
		call HashMap_put(this_2, key, value_2)
	else
		call IterableMap_put(this_2, key, value_2)
	endif
endfunction

function OnCastListener_onDestroy takes integer this_2 returns nothing
	local integer listener
	local integer listener_2
	if OnCastListener_eventUnit[this_2] != null then
		set listener = dispatch_HashMap_HashMap_HashMap_get(EventListener_castMapCasters, unitToIndex(OnCastListener_eventUnit[this_2]))
		if listener == this_2 then
			call dispatch_HashMap_HashMap_HashMap_put(EventListener_castMapCasters, unitToIndex(OnCastListener_eventUnit[this_2]), OnCastListener_next[this_2])
		elseif OnCastListener_prev[this_2] != 0 then
			set OnCastListener_next[OnCastListener_prev[this_2]] = OnCastListener_next[this_2]
		endif
	else
		set listener_2 = dispatch_HashMap_HashMap_HashMap_get(EventListener_castMap, OnCastListener_abilId[this_2])
		if listener_2 == this_2 then
			call dispatch_HashMap_HashMap_HashMap_put(EventListener_castMap, OnCastListener_abilId[this_2], OnCastListener_next[this_2])
		elseif OnCastListener_prev[this_2] != 0 then
			set OnCastListener_next[OnCastListener_prev[this_2]] = OnCastListener_next[this_2]
		endif
	endif
	set OnCastListener_prev[OnCastListener_next[this_2]] = OnCastListener_prev[this_2]
	set OnCastListener_next[this_2] = 0
	set OnCastListener_prev[this_2] = 0
endfunction

function dealloc_OnCastListener takes integer obj returns nothing
	if OnCastListener_typeId[obj] == 0 then
		call error("Double free: object of type OnCastListener")
	else
		set OnCastListener_firstFree = OnCastListener_firstFree + 1
		set OnCastListener_typeId[obj] = 0
	endif
endfunction

function destroyOnCastListener takes integer this_2 returns nothing
	call OnCastListener_onDestroy(this_2)
	call dealloc_OnCastListener(this_2)
endfunction

function dispatch_OnCastListener_destroyOnCastListener takes integer this_2 returns nothing
	if OnCastListener_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling OnCastListener.OnCastListener")
		else
			call error("Called OnCastListener.OnCastListener on invalid object.")
		endif
	endif
	call destroyOnCastListener(this_2)
endfunction

function EventListener_onDestroy takes integer this_2 returns nothing
	local integer listener = EventListener_generalListenersFirsts[EventListener_eventId[this_2]]
	if listener == this_2 then
		set EventListener_generalListenersFirsts[EventListener_eventId[this_2]] = EventListener_next[this_2]
	elseif EventListener_prev[this_2] != 0 then
		set EventListener_next[EventListener_prev[this_2]] = EventListener_next[this_2]
	endif
	set EventListener_prev[EventListener_next[this_2]] = EventListener_prev[this_2]
	set listener = EventListener_unitListenersFirsts[EventListener_eventId[this_2]]
	if listener == this_2 then
		set EventListener_unitListenersFirsts[EventListener_eventId[this_2]] = EventListener_next[this_2]
	elseif EventListener_prev[this_2] != 0 then
		set EventListener_next[EventListener_prev[this_2]] = EventListener_next[this_2]
	endif
	set EventListener_prev[EventListener_next[this_2]] = EventListener_prev[this_2]
	set EventListener_next[this_2] = 0
	set EventListener_prev[this_2] = 0
endfunction

function dealloc_EventListener takes integer obj returns nothing
	if EventListener_typeId[obj] == 0 then
		call error("Double free: object of type EventListener")
	else
		set EventListener_nextFree[EventListener_firstFree] = obj
		set EventListener_firstFree = EventListener_firstFree + 1
		set EventListener_typeId[obj] = 0
	endif
endfunction

function destroyEventListener takes integer this_2 returns nothing
	call EventListener_onDestroy(this_2)
	call dealloc_EventListener(this_2)
endfunction

function dispatch_EventListener_destroyEventListener takes integer this_2 returns nothing
	if EventListener_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling EventListener.EventListener")
		else
			call error("Called EventListener.EventListener on invalid object.")
		endif
	endif
	call destroyEventListener(this_2)
endfunction

function unregisterEvents takes integer id_2 returns nothing
	local integer listener
	local integer t
	if id_2 > 0 then
		if EventListener_unitListenersFirsts[id_2] != 0 then
			call Log_trace("unregister unit has listeners. startid: " + int_toString(id_2))
			set listener = EventListener_unitListenersFirsts[id_2]
			set EventListener_unitListenersFirsts[id_2] = 0
			loop
				exitwhen  not (listener != 0)
				set t = listener
				set listener = EventListener_next[listener]
				call dispatch_EventListener_destroyEventListener(t)
			endloop
		endif
	endif
endfunction

function unregisterEventsForUnit takes unit u returns nothing
	local integer listener
	local integer t
	local integer listener_2
	local integer t_2
	if fireEvents(u) then
		call unregisterEvents(unit_getIndex(u))
		if dispatch_HashMap_HashMap_HashMap_has(EventListener_castMapCasters, unitToIndex(u)) then
			set listener = dispatch_HashMap_HashMap_HashMap_get(EventListener_castMapCasters, unitToIndex(u))
			call dispatch_HashMap_HashMap_HashMap_remove(EventListener_castMapCasters, unitToIndex(u))
			loop
				exitwhen  not (listener != 0)
				set t = listener
				set listener = OnCastListener_next[listener]
				call dispatch_OnCastListener_destroyOnCastListener(t)
			endloop
		endif
		if dispatch_HashMap_HashMap_HashMap_has(EventListener_castMapCasters, unitToIndex(u)) then
			set listener_2 = dispatch_HashMap_HashMap_HashMap_get(EventListener_castMapCasters, unitToIndex(u))
			loop
				exitwhen  not (listener_2 != 0)
				set t_2 = listener_2
				set listener_2 = OnCastListener_next[listener_2]
				call dispatch_OnCastListener_destroyOnCastListener(t_2)
			endloop
		endif
	endif
endfunction

function code__onUnitDeindex_ClosureEvents takes nothing returns nothing
	call unregisterEventsForUnit(getIndexingUnit())
endfunction

function unit_clearLastOrders takes unit this_2 returns nothing
	local integer index = unit_getIndex(this_2)
	if LastOrder_lastOrder[index] != 0 then
		call cyc_destroyOrder(1, LastOrder_lastOrder[index])
		set LastOrder_lastOrder[index] = 0
	endif
endfunction

function code__onUnitDeindex_LastOrder takes nothing returns nothing
	call unit_clearLastOrders(getIndexingUnit())
endfunction

function registerEventsForUnit takes unit u returns nothing
	if fireEvents(u) then
		call trigger_registerUnitEvent(ClosureEvents_unitTrig, u, EVENT_UNIT_DAMAGED)
	endif
endfunction

function code__onUnitIndex_ClosureEvents takes nothing returns nothing
	call registerEventsForUnit(getIndexingUnit())
endfunction

function hashtable_loadTriggerHandle takes hashtable this_2, integer parentKey, integer childKey returns trigger
	return LoadTriggerHandle(this_2, parentKey, childKey)
endfunction

function Table_loadTrigger takes integer this_2, integer parentKey returns trigger
	return hashtable_loadTriggerHandle(Table_ht, this_2, parentKey)
endfunction

function dispatch_Table_Table_Table_loadTrigger takes integer this_2, integer parentKey returns trigger
	local trigger Table_Table_loadTrigger_result
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.loadTrigger")
		else
			call error("Called Table.loadTrigger on invalid object.")
		endif
	endif
	set Table_Table_loadTrigger_result = Table_loadTrigger(this_2, parentKey)
	set dispatch_Table_Table_Table_loadTriggertempReturn = Table_Table_loadTrigger_result
	set Table_Table_loadTrigger_result = null
	return dispatch_Table_Table_Table_loadTriggertempReturn
endfunction

function hashtable_saveFogStateHandle takes hashtable this_2, integer parentKey, integer childKey, fogstate value_2 returns nothing
	call SaveFogStateHandle(this_2, parentKey, childKey, value_2)
endfunction

function Table_saveFogState takes integer this_2, integer parentKey, fogstate value_2 returns nothing
	call hashtable_saveFogStateHandle(Table_ht, this_2, parentKey, value_2)
endfunction

function dispatch_Table_Table_Table_saveFogState takes integer this_2, integer parentKey, fogstate value_2 returns nothing
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.saveFogState")
		else
			call error("Called Table.saveFogState on invalid object.")
		endif
	endif
	call Table_saveFogState(this_2, parentKey, value_2)
endfunction

function triggerFromIndex takes integer index returns trigger
	call dispatch_Table_Table_Table_saveFogState(TypeCasting_typecastdata, 0, ConvertFogState(index))
	return dispatch_Table_Table_Table_loadTrigger(TypeCasting_typecastdata, 0)
endfunction

function code__registerPlayerUnitEvent_RegisterEvents takes nothing returns boolean
	return trigger_evaluate(triggerFromIndex(dispatch_HashMap_HashMap_HashMap_get(RegisterEvents_onCastMap, GetSpellAbilityId())))
endfunction

function dispatch_OnCast_ClosureEvents_OnCast_fireEx takes integer this_2, integer id_2 returns nothing
endfunction

function OnCast_fire takes integer this_2, unit caster returns nothing
	call dispatch_OnCast_ClosureEvents_OnCast_fireEx(this_2, GetSpellAbilityId())
endfunction

function dispatch_OnPointCast_ClosureEvents_OnPointCast_fireEx takes integer this_2, unit caster, real target_x, real target_y returns nothing
endfunction

function getSpellTargetPos takes nothing returns real
	set getSpellTargetPos_return_x = GetSpellTargetX()
	set getSpellTargetPos_return_y = GetSpellTargetY()
	return getSpellTargetPos_return_x
endfunction

function OnPointCast_fire takes integer this_2, unit caster returns nothing
	call dispatch_OnPointCast_ClosureEvents_OnPointCast_fireEx(this_2, caster, getSpellTargetPos(), getSpellTargetPos_return_y)
endfunction

function dispatch_OnUnitCast_ClosureEvents_OnUnitCast_fireEx takes integer this_2, unit caster, unit target returns nothing
endfunction

function OnUnitCast_fire takes integer this_2, unit caster returns nothing
	call dispatch_OnUnitCast_ClosureEvents_OnUnitCast_fireEx(this_2, caster, GetSpellTargetUnit())
endfunction

function print takes string msg returns nothing
	call DisplayTimedTextToPlayer(Player_localPlayer, 0., 0., Printing_DEBUG_MSG_DURATION, msg)
endfunction

function fire_onCast_ClosureEventsTests takes integer this_2, unit caster returns nothing
	call print("OnCast")
endfunction

function dispatch_OnCastListener_ClosureEvents_OnCastListener_fire takes integer this_2, unit caster returns nothing
	if OnCastListener_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling OnCastListener.fire")
		else
			call error("Called OnCastListener.fire on invalid object.")
		endif
	endif
	if OnCastListener_typeId[this_2] <= 812 then
		if OnCastListener_typeId[this_2] <= 811 then
			call OnCast_fire(this_2, caster)
		else
			call fire_onCast_ClosureEventsTests(this_2, caster)
		endif
	elseif OnCastListener_typeId[this_2] <= 813 then
		call OnPointCast_fire(this_2, caster)
	else
		call OnUnitCast_fire(this_2, caster)
	endif
endfunction

function EventListener_onSpellEffect takes nothing returns nothing
	local unit trigUnit = GetTriggerUnit()
	local integer abilId = GetSpellAbilityId()
	local integer listener
	local integer listener_2
	if dispatch_HashMap_HashMap_HashMap_has(EventListener_castMapCasters, unitToIndex(trigUnit)) then
		set listener = dispatch_HashMap_HashMap_HashMap_get(EventListener_castMapCasters, unitToIndex(trigUnit))
		loop
			exitwhen  not (listener != 0)
			if OnCastListener_abilId[listener] == -1 or OnCastListener_abilId[listener] == abilId then
				call dispatch_OnCastListener_ClosureEvents_OnCastListener_fire(listener, GetSpellAbilityUnit())
			endif
			set listener = OnCastListener_next[listener]
		endloop
	endif
	if dispatch_HashMap_HashMap_HashMap_has(EventListener_castMap, abilId) then
		set listener_2 = dispatch_HashMap_HashMap_HashMap_get(EventListener_castMap, abilId)
		loop
			exitwhen  not (listener_2 != 0)
			if OnCastListener_eventUnit[listener_2] == null or OnCastListener_eventUnit[listener_2] == trigUnit then
				call dispatch_OnCastListener_ClosureEvents_OnCastListener_fire(listener_2, GetSpellAbilityUnit())
			endif
			set listener_2 = OnCastListener_next[listener_2]
		endloop
	endif
	set trigUnit = null
endfunction

function code__registerPlayerUnitEvent_nullTimer_ClosureEvents takes nothing returns nothing
	call EventListener_onSpellEffect()
endfunction

function trigger_execute takes trigger this_2 returns nothing
	call TriggerExecute(this_2)
endfunction

function unit_getAbilityLevel takes unit this_2, integer id_2 returns integer
	return GetUnitAbilityLevel(this_2, id_2)
endfunction

function code__registerPlayerUnitEvent_nullTimer_OnUnitEnterLeave takes nothing returns nothing
	local unit leavingUnit = GetTriggerUnit()
	if unit_getAbilityLevel(leavingUnit, OnUnitEnterLeave_ABILITY_ID) == 0 then
		call pushUnit(leavingUnit)
		call trigger_execute(OnUnitEnterLeave_eventTrigger)
		call popUnit()
	endif
	set leavingUnit = null
endfunction

function code__startPeriodic_GameTimer takes nothing returns nothing
	set GameTimer_currentTime = GameTimer_currentTime + Basics_ANIMATION_PERIOD
endfunction

function call_doAfter_ClosureTimers takes integer this_2 returns nothing
	set ClosureTimers_x = ClosureTimers_x + 50
endfunction

function call_doAfter_ClosureTimers_565 takes integer this_2 returns nothing
	set ClosureTimers_x = ClosureTimers_x * 2
endfunction

function testFail takes string msg returns nothing
endfunction

function int_assertEquals takes integer this_2, integer expected returns nothing
	if this_2 != expected then
		call testFail("Expected <" + int_toString(expected) + ">, Actual <" + int_toString(this_2) + ">")
	endif
endfunction

function call_doAfter_ClosureTimers_566 takes integer this_2 returns nothing
	set ClosureTimers_x = ClosureTimers_x / 2
	call int_assertEquals(ClosureTimers_x, 250)
endfunction

function DummyCaster_onDestroy takes integer this_2 returns nothing
endfunction

function dealloc_DummyCaster takes integer obj returns nothing
	if DummyCaster_typeId[obj] == 0 then
		call error("Double free: object of type DummyCaster")
	else
		set DummyCaster_firstFree = DummyCaster_firstFree + 1
		set DummyCaster_typeId[obj] = 0
	endif
endfunction

function destroyDummyCaster takes integer this_2 returns nothing
	call DummyCaster_onDestroy(this_2)
	call dealloc_DummyCaster(this_2)
endfunction

function dispatch_DummyCaster_destroyDummyCaster takes integer this_2 returns nothing
	if DummyCaster_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling DummyCaster.DummyCaster")
		else
			call error("Called DummyCaster.DummyCaster on invalid object.")
		endif
	endif
	call destroyDummyCaster(this_2)
endfunction

function unit_removeAbility takes unit this_2, integer abil returns boolean
	return UnitRemoveAbility(this_2, abil)
endfunction

function call_doAfter_DummyCaster_DummyCaster takes integer this_2 returns nothing
	local unit receiver = dummy[this_2]
	call unit_removeAbility(receiver, id[this_2])
	call DummyRecycler_recycle(dummy[this_2])
	set DummyCaster_castCount[this_795[this_2]] = DummyCaster_castCount[this_795[this_2]] - 1
	if DummyCaster_castCount[this_795[this_2]] == 0 then
		call dispatch_DummyCaster_destroyDummyCaster(this_795[this_2])
	endif
	set receiver = null
endfunction

function dispatch_LocalFilesCallback_LocalFiles_LocalFilesCallback_run takes integer this_2 returns nothing
endfunction

function LocalFiles_checkAll takes nothing returns nothing
	local integer iterator = LinkedList_iterator(LocalFiles_callbacks)
	local integer cb
	loop
		exitwhen  not LLIterator_hasNext(iterator)
		set cb = LLIterator_next(iterator)
		call dispatch_LocalFilesCallback_LocalFiles_LocalFilesCallback_run(cb)
	endloop
	call LLIterator_close(iterator)
endfunction

function call_doAfter_LocalFiles takes integer this_2 returns nothing
	call LocalFiles_checkAll()
endfunction

function trigger_registerPlayerChatEvent takes trigger this_2, player whichPlayer, string chatMessageToDetect, boolean exactMatchOnly returns event
	return TriggerRegisterPlayerChatEvent(this_2, whichPlayer, chatMessageToDetect, exactMatchOnly)
endfunction

function call_nullTimer_ClosureEvents takes integer this_2 returns nothing
	local integer i_2
	local integer temp
	local trigger receiver
	local trigger receiver_2
	local trigger receiver_3
	local trigger receiver_4
	local trigger receiver_5
	local trigger receiver_6
	local trigger receiver_7
	local trigger receiver_8
	local trigger receiver_9
	local trigger receiver_10
	call trigger_addAction(ClosureEvents_unitTrig, ref_function_code__addAction_nullTimer_ClosureEvents)
	call trigger_addAction(ClosureEvents_leaveTrig, ref_function_code__addAction_nullTimer_ClosureEvents_679)
	call trigger_addAction(ClosureEvents_keyTrig, ref_function_code__addAction_nullTimer_ClosureEvents_680)
	set i_2 = 0
	set temp = bj_MAX_PLAYERS - 1
	loop
		exitwhen i_2 > temp
		call trigger_registerPlayerEvent(ClosureEvents_leaveTrig, Player_players[i_2], EVENT_PLAYER_LEAVE)
		set receiver = ClosureEvents_keyTrig
		call trigger_registerPlayerEvent(receiver, Player_players[i_2], EVENT_PLAYER_ARROW_DOWN_DOWN)
		set receiver_2 = receiver
		call trigger_registerPlayerEvent(receiver_2, Player_players[i_2], EVENT_PLAYER_ARROW_DOWN_UP)
		set receiver_3 = receiver_2
		call trigger_registerPlayerEvent(receiver_3, Player_players[i_2], EVENT_PLAYER_ARROW_UP_DOWN)
		set receiver_4 = receiver_3
		call trigger_registerPlayerEvent(receiver_4, Player_players[i_2], EVENT_PLAYER_ARROW_UP_UP)
		set receiver_5 = receiver_4
		call trigger_registerPlayerEvent(receiver_5, Player_players[i_2], EVENT_PLAYER_ARROW_LEFT_DOWN)
		set receiver_6 = receiver_5
		call trigger_registerPlayerEvent(receiver_6, Player_players[i_2], EVENT_PLAYER_ARROW_LEFT_UP)
		set receiver_7 = receiver_6
		call trigger_registerPlayerEvent(receiver_7, Player_players[i_2], EVENT_PLAYER_ARROW_RIGHT_DOWN)
		set receiver_8 = receiver_7
		call trigger_registerPlayerEvent(receiver_8, Player_players[i_2], EVENT_PLAYER_ARROW_RIGHT_UP)
		set receiver_9 = receiver_8
		call trigger_registerPlayerEvent(receiver_9, Player_players[i_2], EVENT_PLAYER_END_CINEMATIC)
		set receiver_10 = receiver_9
		call trigger_registerPlayerChatEvent(receiver_10, Player_players[i_2], "", false)
		set i_2 = i_2 + 1
	endloop
	call registerPlayerUnitEvent_1254(EVENT_PLAYER_UNIT_SPELL_EFFECT, null, ref_function_code__registerPlayerUnitEvent_nullTimer_ClosureEvents, null)
	set receiver = null
	set receiver_2 = null
	set receiver_3 = null
	set receiver_4 = null
	set receiver_5 = null
	set receiver_6 = null
	set receiver_7 = null
	set receiver_8 = null
	set receiver_9 = null
	set receiver_10 = null
endfunction

function group_destr takes group this_2 returns nothing
	call DestroyGroup(this_2)
endfunction

function group_enumUnitsInRect_1041 takes group this_2, rect rec, boolexpr filter returns nothing
	call GroupEnumUnitsInRect(this_2, rec, filter)
endfunction

function group_enumUnitsInRect takes group this_2, rect rec returns nothing
	call group_enumUnitsInRect_1041(this_2, rec, null)
endfunction

function trigger_registerEnterRegion takes trigger this_2, region whichRegion, boolexpr filter returns event
	return TriggerRegisterEnterRegion(this_2, whichRegion, filter)
endfunction

function call_nullTimer_OnUnitEnterLeave takes integer this_2 returns nothing
	local trigger receiver = CreateTrigger()
	local group receiver_2
	local group receiver_3
	call trigger_registerEnterRegion(receiver, MapBounds_boundRegion, Filter(ref_function_code__Filter_registerEnterRegion_nullTimer_OnUnitEnterLeave))
	call registerPlayerUnitEvent(EVENT_PLAYER_UNIT_ISSUED_ORDER, ref_function_code__registerPlayerUnitEvent_nullTimer_OnUnitEnterLeave)
	call group_enumUnitsInRect(OnUnitEnterLeave_preplacedUnits, MapBounds_boundRect)
	call ForGroup(OnUnitEnterLeave_preplacedUnits, ref_function_code__ForGroup_nullTimer_OnUnitEnterLeave)
	set receiver_2 = OnUnitEnterLeave_preplacedUnits
	call group_clear(receiver_2)
	set receiver_3 = receiver_2
	call group_destr(receiver_3)
	set receiver = null
	set receiver_2 = null
	set receiver_3 = null
endfunction

function alloc_ForGroupCallback_forEachFrom_Preloader takes nothing returns integer
	local integer this_2
	if ForGroupCallback_firstFree == 0 then
		if ForGroupCallback_maxIndex < 32768 then
			set ForGroupCallback_maxIndex = ForGroupCallback_maxIndex + 1
			set this_2 = ForGroupCallback_maxIndex
			set ForGroupCallback_typeId[this_2] = 709
		else
			call error("Out of memory: Could not create ForGroupCallback_forEachFrom_Preloader.")
			set this_2 = 0
		endif
	else
		set ForGroupCallback_firstFree = ForGroupCallback_firstFree - 1
		set this_2 = ForGroupCallback_nextFree[ForGroupCallback_firstFree]
		set ForGroupCallback_typeId[this_2] = 709
	endif
	return this_2
endfunction

function group_hasNext takes group this_2 returns boolean
	return FirstOfGroup(this_2) != null
endfunction

function group_next takes group this_2 returns unit
	local unit iterUnit = FirstOfGroup(this_2)
	call GroupRemoveUnit(this_2, iterUnit)
	set group_nexttempReturn = iterUnit
	set iterUnit = null
	return group_nexttempReturn
endfunction

function group_forEachFrom takes group this_2, integer cb returns nothing
	local group from = this_2
	local unit u
	loop
		exitwhen  not group_hasNext(from)
		set u = group_next(from)
		call dispatch_ForGroupCallback_ClosureForGroups_ForGroupCallback_callback(cb, u)
	endloop
	call dispatch_ForGroupCallback_destroyForGroupCallback(cb)
	set u = null
	set from = null
endfunction

function finishPreload takes nothing returns nothing
	local integer clVar
	local group temp
	call unit_remove(Preloader_dum)
	set temp = Preloader_dumg
	set clVar = alloc_ForGroupCallback_forEachFrom_Preloader()
	call group_forEachFrom(temp, clVar)
	call group_destr(Preloader_dumg)
	set Preloader_dumg = null
	set temp = null
endfunction

function call_nullTimer_Preloader takes integer this_2 returns nothing
	call finishPreload()
endfunction

function dispatch_CallbackSingle_ClosureTimers_CallbackSingle_call takes integer this_2 returns nothing
	if CallbackSingle_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling CallbackSingle.call")
		else
			call error("Called CallbackSingle.call on invalid object.")
		endif
	endif
	if CallbackSingle_typeId[this_2] <= 657 then
		if CallbackSingle_typeId[this_2] <= 655 then
			if CallbackSingle_typeId[this_2] <= 654 then
				call call_doAfter_ClosureTimers(this_2)
			else
				call call_doAfter_ClosureTimers_565(this_2)
			endif
		elseif CallbackSingle_typeId[this_2] <= 656 then
			call call_doAfter_ClosureTimers_566(this_2)
		else
			call call_doAfter_DummyCaster_DummyCaster(this_2)
		endif
	elseif CallbackSingle_typeId[this_2] <= 659 then
		if CallbackSingle_typeId[this_2] <= 658 then
			call call_doAfter_LocalFiles(this_2)
		else
			call call_nullTimer_ClosureEvents(this_2)
		endif
	elseif CallbackSingle_typeId[this_2] <= 660 then
		call call_nullTimer_OnUnitEnterLeave(this_2)
	else
		call call_nullTimer_Preloader(this_2)
	endif
endfunction

function CallbackSingle_onDestroy takes integer this_2 returns nothing
	call timer_release(CallbackSingle_t[this_2])
endfunction

function dealloc_CallbackSingle takes integer obj returns nothing
	if CallbackSingle_typeId[obj] == 0 then
		call error("Double free: object of type CallbackSingle")
	else
		set CallbackSingle_nextFree[CallbackSingle_firstFree] = obj
		set CallbackSingle_firstFree = CallbackSingle_firstFree + 1
		set CallbackSingle_typeId[obj] = 0
	endif
endfunction

function destroyCallbackSingle takes integer this_2 returns nothing
	call CallbackSingle_onDestroy(this_2)
	call dealloc_CallbackSingle(this_2)
endfunction

function dispatch_CallbackSingle_destroyCallbackSingle takes integer this_2 returns nothing
	if CallbackSingle_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling CallbackSingle.CallbackSingle")
		else
			call error("Called CallbackSingle.CallbackSingle on invalid object.")
		endif
	endif
	call destroyCallbackSingle(this_2)
endfunction

function CallbackSingle_staticCallback takes nothing returns nothing
	local timer t = GetExpiredTimer()
	local integer cb = timer_getData(t)
	call dispatch_CallbackSingle_ClosureTimers_CallbackSingle_call(cb)
	call dispatch_CallbackSingle_destroyCallbackSingle(cb)
	set t = null
endfunction

function code__start_CallbackSingle_ClosureTimers takes nothing returns nothing
	call CallbackSingle_staticCallback()
endfunction

function alloc_ForForceCallback_try_load_PersistentStore_Persistable takes nothing returns integer
	local integer this_2
	if ForForceCallback_firstFree == 0 then
		if ForForceCallback_maxIndex < 32768 then
			set ForForceCallback_maxIndex = ForForceCallback_maxIndex + 1
			set this_2 = ForForceCallback_maxIndex
			set ForForceCallback_typeId[this_2] = 704
		else
			call error("Out of memory: Could not create ForForceCallback_try_load_PersistentStore_Persistable.")
			set this_2 = 0
		endif
	else
		set ForForceCallback_firstFree = ForForceCallback_firstFree - 1
		set this_2 = ForForceCallback_nextFree[ForForceCallback_firstFree]
		set ForForceCallback_typeId[this_2] = 704
	endif
	return this_2
endfunction

function alloc_ForForceCallback_try_load_PersistentStore_Persistable_494 takes nothing returns integer
	local integer this_2
	if ForForceCallback_firstFree == 0 then
		if ForForceCallback_maxIndex < 32768 then
			set ForForceCallback_maxIndex = ForForceCallback_maxIndex + 1
			set this_2 = ForForceCallback_maxIndex
			set ForForceCallback_typeId[this_2] = 705
		else
			call error("Out of memory: Could not create ForForceCallback_try_load_PersistentStore_Persistable.")
			set this_2 = 0
		endif
	else
		set ForForceCallback_firstFree = ForForceCallback_firstFree - 1
		set this_2 = ForForceCallback_nextFree[ForForceCallback_firstFree]
		set ForForceCallback_typeId[this_2] = 705
	endif
	return this_2
endfunction

function HashBuffer_clear takes integer this_2 returns nothing
	call dispatch_Table_Table_Table_flush(HashBuffer_buffer[this_2])
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_clear takes integer this_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.clear")
		else
			call error("Called HashBuffer.clear on invalid object.")
		endif
	endif
	call HashBuffer_clear(this_2)
endfunction

function AbstractBuffer_isWriteable takes integer this_2 returns boolean
	return AbstractBuffer_mode[this_2] == 1 or AbstractBuffer_mode[this_2] == 2
endfunction

function dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_isWriteable takes integer this_2 returns boolean
	local boolean BufferInterface_AbstractBuffer_isWriteable_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractBuffer.isWriteable")
		else
			call error("Called AbstractBuffer.isWriteable on invalid object.")
		endif
	endif
	set BufferInterface_AbstractBuffer_isWriteable_result = AbstractBuffer_isWriteable(this_2)
	return BufferInterface_AbstractBuffer_isWriteable_result
endfunction

function AbstractBuffer_checkWrite takes integer this_2 returns nothing
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkFailed(this_2)
	if SafetyChecks_SAFETY_CHECKS_ENABLED and ( not dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_isWriteable(this_2)) then
		call error("Buffer: cannot write to buffer with mode " + BufferMode_toString(AbstractBuffer_mode[this_2]))
	endif
endfunction

function dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkWrite takes integer this_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractBuffer.checkWrite")
		else
			call error("Called AbstractBuffer.checkWrite on invalid object.")
		endif
	endif
	call AbstractBuffer_checkWrite(this_2)
endfunction

function Table_saveBoolean takes integer this_2, integer parentKey, boolean value_2 returns nothing
	call hashtable_saveBoolean(Table_ht, this_2, parentKey, value_2)
endfunction

function dispatch_Table_Table_Table_saveBoolean takes integer this_2, integer parentKey, boolean value_2 returns nothing
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.saveBoolean")
		else
			call error("Called Table.saveBoolean on invalid object.")
		endif
	endif
	call Table_saveBoolean(this_2, parentKey, value_2)
endfunction

function HashBuffer_writeBoolean takes integer this_2, boolean value_2 returns nothing
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkWrite(this_2)
	set HashBuffer_booleanWriteIndex[this_2] = HashBuffer_booleanWriteIndex[this_2] + 1
	call dispatch_Table_Table_Table_saveBoolean(HashBuffer_buffer[this_2], HashBuffer_booleanWriteIndex[this_2], value_2)
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_writeBoolean takes integer this_2, boolean value_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.writeBoolean")
		else
			call error("Called HashBuffer.writeBoolean on invalid object.")
		endif
	endif
	call HashBuffer_writeBoolean(this_2, value_2)
endfunction

function Network_getData takes integer this_2 returns integer
	return Network_dataBuffer[this_2]
endfunction

function dispatch_Network_Network_Network_getData takes integer this_2 returns integer
	local integer Network_Network_getData_result
	if Network_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Network.getData")
		else
			call error("Called Network.getData on invalid object.")
		endif
	endif
	set Network_Network_getData_result = Network_getData(this_2)
	return Network_Network_getData_result
endfunction

function AbstractStringBuffer_onDestroy takes integer this_2 returns nothing
	local integer iterator = LinkedList_iterator(AbstractStringBuffer_chunks[this_2])
	local integer chunk
	loop
		exitwhen  not LLIterator_hasNext(iterator)
		set chunk = LLIterator_next(iterator)
		call dispatch_ChunkElement_destroyChunkElement(chunk)
	endloop
	call LLIterator_close(iterator)
	call dispatch_LinkedList_destroyLinkedList(AbstractStringBuffer_chunks[this_2])
	call AbstractBuffer_onDestroy(this_2)
endfunction

function OrderedStringBuffer_onDestroy takes integer this_2 returns nothing
	call AbstractStringBuffer_onDestroy(this_2)
endfunction

function dealloc_OrderedStringBuffer takes integer obj returns nothing
	if Buffer_typeId[obj] == 0 then
		call error("Double free: object of type OrderedStringBuffer")
	else
		set Buffer_nextFree[Buffer_firstFree] = obj
		set Buffer_firstFree = Buffer_firstFree + 1
		set Buffer_typeId[obj] = 0
	endif
endfunction

function destroyOrderedStringBuffer takes integer this_2 returns nothing
	call OrderedStringBuffer_onDestroy(this_2)
	call dealloc_OrderedStringBuffer(this_2)
endfunction

function dispatch_OrderedStringBuffer_destroyOrderedStringBuffer takes integer this_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling OrderedStringBuffer.OrderedStringBuffer")
		else
			call error("Called OrderedStringBuffer.OrderedStringBuffer on invalid object.")
		endif
	endif
	call destroyOrderedStringBuffer(this_2)
endfunction

function alloc_OrderedStringBuffer takes nothing returns integer
	local integer this_2
	if Buffer_firstFree == 0 then
		if Buffer_maxIndex < 32768 then
			set Buffer_maxIndex = Buffer_maxIndex + 1
			set this_2 = Buffer_maxIndex
			set Buffer_typeId[this_2] = 644
		else
			call error("Out of memory: Could not create OrderedStringBuffer.")
			set this_2 = 0
		endif
	else
		set Buffer_firstFree = Buffer_firstFree - 1
		set this_2 = Buffer_nextFree[Buffer_firstFree]
		set Buffer_typeId[this_2] = 644
	endif
	return this_2
endfunction

function construct_AbstractBuffer takes integer this_2 returns nothing
	set AbstractBuffer_mode[this_2] = 2
	set AbstractBuffer_failureMode[this_2] = 0
endfunction

function min_2 takes integer numbers_0, integer numbers_1 returns integer
	local integer minNumber = Integer_INT_MAX
	local integer cond_result
	if numbers_0 < minNumber then
		set cond_result = numbers_0
	else
		set cond_result = minNumber
	endif
	set minNumber = cond_result
	if numbers_1 < minNumber then
		set cond_result = numbers_1
	else
		set cond_result = minNumber
	endif
	set minNumber = cond_result
	return minNumber
endfunction

function alloc_LinkedList takes nothing returns integer
	local integer this_2
	if LinkedList_firstFree == 0 then
		if LinkedList_maxIndex < 32768 then
			set LinkedList_maxIndex = LinkedList_maxIndex + 1
			set this_2 = LinkedList_maxIndex
			set LinkedList_typeId[this_2] = 790
		else
			call error("Out of memory: Could not create LinkedList.")
			set this_2 = 0
		endif
	else
		set LinkedList_firstFree = LinkedList_firstFree - 1
		set this_2 = LinkedList_nextFree[LinkedList_firstFree]
		set LinkedList_typeId[this_2] = 790
	endif
	return this_2
endfunction

function construct_LinkedList2 takes integer this_2 returns nothing
	set LinkedList_dummy[this_2] = new_LLEntry(0, 0, 0)
	set LinkedList_size[this_2] = 0
	set LinkedList_staticItr[this_2] = 0
	set LinkedList_staticBackItr[this_2] = 0
	set LLEntry_next[LinkedList_dummy[this_2]] = LinkedList_dummy[this_2]
	set LLEntry_prev[LinkedList_dummy[this_2]] = LinkedList_dummy[this_2]
endfunction

function new_LinkedList takes nothing returns integer
	local integer this_2 = alloc_LinkedList()
	call construct_LinkedList2(this_2)
	return this_2
endfunction

function construct_AbstractStringBuffer takes integer this_2, integer maxSize returns nothing
	call construct_AbstractBuffer(this_2)
	set AbstractStringBuffer_chunks[this_2] = new_LinkedList()
	set AbstractStringBuffer_readBuffer[this_2] = ""
	set AbstractStringBuffer_writeBuffer[this_2] = ""
	if maxSize > AbstractStringBuffer_MAX_BACKBUFFER_SIZE then
		call Log_warn("Trying to instantiate an instance of AbstractStringBuffer with maxSize=" + int_toString(maxSize) + ", but MAX_BACKBUFFER_SIZE is " + int_toString(AbstractStringBuffer_MAX_BACKBUFFER_SIZE))
		call Log_warn("The value will be clamped to " + int_toString(AbstractStringBuffer_MAX_BACKBUFFER_SIZE))
	endif
	set AbstractStringBuffer_maxBufferSize[this_2] = min_2(maxSize, AbstractStringBuffer_MAX_BACKBUFFER_SIZE)
endfunction

function construct_OrderedStringBuffer2 takes integer this_2, integer maxBufferSize returns nothing
	call construct_AbstractStringBuffer(this_2, maxBufferSize)
endfunction

function new_OrderedStringBuffer takes integer maxBufferSize returns integer
	local integer this_2 = alloc_OrderedStringBuffer()
	call construct_OrderedStringBuffer2(this_2, maxBufferSize)
	return this_2
endfunction

function run_load_PersistentStore_Persistable takes integer this_2, integer w_status returns nothing
	local integer buffer_2 = new_OrderedStringBuffer(PreloadIO_MAX_PACKET_LENGTH)
	local boolean transferSuccess
	local boolean populateSuccess
	local integer clVar
	local integer clVar_2
	call dispatch_HashBuffer_HashBuffer_HashBuffer_writeBoolean(dispatch_Network_Network_Network_getData(network[this_2]), true)
	set clVar = alloc_ForForceCallback_try_load_PersistentStore_Persistable()
	set buffer_613[clVar] = buffer_2
	set reader_662[clVar] = reader_661[this_2]
	set populateSuccess = try(clVar)
	if populateSuccess then
		set clVar_2 = alloc_ForForceCallback_try_load_PersistentStore_Persistable_494()
		set buffer_614[clVar_2] = buffer_2
		set network_656[clVar_2] = network[this_2]
		set transferSuccess = try(clVar_2)
	else
		set transferSuccess = false
	endif
	if ( not populateSuccess) or ( not transferSuccess) or ( not dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_isValid(buffer_2)) then
		call dispatch_HashBuffer_HashBuffer_HashBuffer_clear(dispatch_Network_Network_Network_getData(network[this_2]))
		call dispatch_HashBuffer_HashBuffer_HashBuffer_writeBoolean(dispatch_Network_Network_Network_getData(network[this_2]), false)
	endif
	call dispatch_OrderedStringBuffer_destroyOrderedStringBuffer(buffer_2)
	call dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_sync(synchronizer[this_2])
endfunction

function dispatch_FileLoadCallback_MultifileIO_FileLoadCallback_run takes integer this_2, integer status returns nothing
	if FileLoadCallback_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling FileLoadCallback.run")
		else
			call error("Called FileLoadCallback.run on invalid object.")
		endif
	endif
	call run_load_PersistentStore_Persistable(this_2, status)
endfunction

function run_onComplete_FileReader_MultifileIO takes integer this_2 returns nothing
	call dispatch_FileLoadCallback_MultifileIO_FileLoadCallback_run(callback_621[this_2], 0)
endfunction

function run_save_PersistentStore_Persistable takes integer this_2 returns nothing
	call dispatch_SimpleSynchronizer_SyncSimple_SimpleSynchronizer_sync(synchronizer_787[this_2])
endfunction

function dispatch_FileSaveCallback_MultifileIO_FileSaveCallback_run takes integer this_2 returns nothing
	if FileSaveCallback_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling FileSaveCallback.run")
		else
			call error("Called FileSaveCallback.run on invalid object.")
		endif
	endif
	call run_save_PersistentStore_Persistable(this_2)
endfunction

function run_onComplete_FileWriter_MultifileIO takes integer this_2 returns nothing
	call dispatch_FileSaveCallback_MultifileIO_FileSaveCallback_run(callback_620[this_2])
endfunction

function alloc_IOTask_submit_FileReader_MultifileIO takes nothing returns integer
	local integer this_2
	if IOTask_firstFree == 0 then
		if IOTask_maxIndex < 32768 then
			set IOTask_maxIndex = IOTask_maxIndex + 1
			set this_2 = IOTask_maxIndex
			set IOTask_typeId[this_2] = 722
		else
			call error("Out of memory: Could not create IOTask_submit_FileReader_MultifileIO.")
			set this_2 = 0
		endif
	else
		set IOTask_firstFree = IOTask_firstFree - 1
		set this_2 = IOTask_nextFree[IOTask_firstFree]
		set IOTask_typeId[this_2] = 722
	endif
	return this_2
endfunction

function AbstractIOTaskExecutor_submit takes integer this_2, integer task returns nothing
	call dispatch_LinkedList_LinkedList_LinkedList_add_1(AbstractIOTaskExecutor_taskQueue[this_2], task)
endfunction

function dispatch_IOTaskExecutor_IOTaskExecutor_IOTaskExecutor_submit takes integer this_2, integer task returns nothing
	if IOTaskExecutor_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling IOTaskExecutor.submit")
		else
			call error("Called IOTaskExecutor.submit on invalid object.")
		endif
	endif
	call AbstractIOTaskExecutor_submit(this_2, task)
endfunction

function FileReader_readChunk takes integer this_2, integer chunkId_2 returns nothing
	local integer temp = AbstractFile_executor[this_2]
	local integer clVar = alloc_IOTask_submit_FileReader_MultifileIO()
	set this_806[clVar] = this_2
	set chunkId_624[clVar] = chunkId_2
	call dispatch_IOTaskExecutor_IOTaskExecutor_IOTaskExecutor_submit(temp, clVar)
endfunction

function IOReader_getRemainingReads takes nothing returns integer
	return IOReader_packetCount - IOReader_packetNumber
endfunction

function IOReader_canRead takes nothing returns boolean
	return IOReader_getRemainingReads() > 0
endfunction

function IOReader_getPacket takes integer i_2 returns string
	return IOReader_packets[i_2]
endfunction

function player_setName takes player this_2, string name returns nothing
	call SetPlayerName(this_2, name)
endfunction

function IOReader_restoreNames takes nothing returns nothing
	local integer i_2 = 0
	local integer temp = PreloadIO_PACKETS_PER_FILE - 1
	loop
		exitwhen i_2 > temp
		call player_setName(Player_players[i_2], IOReader_playerNames[i_2])
		set i_2 = i_2 + 1
	endloop
endfunction

function player_getName takes player this_2 returns string
	return GetPlayerName(this_2)
endfunction

function IOReader_saveNames takes nothing returns nothing
	local integer i_2 = 0
	local integer temp = PreloadIO_PACKETS_PER_FILE - 1
	loop
		exitwhen i_2 > temp
		set IOReader_playerNames[i_2] = player_getName(Player_players[i_2])
		set IOReader_packets[i_2] = null
		set i_2 = i_2 + 1
	endloop
endfunction

function IOReader_load takes string path returns nothing
	local integer i_2
	local integer temp
	set IOReader_packetNumber = 0
	set IOReader_packetCount = 0
	call IOReader_saveNames()
	call Preloader(path)
	set i_2 = 0
	set temp = PreloadIO_PACKETS_PER_FILE - 1
	loop
		exitwhen i_2 > temp
		if IOReader_playerNames[i_2] != player_getName(Player_players[i_2]) then
			set IOReader_packets[i_2] = player_getName(Player_players[i_2])
			set IOReader_packetCount = IOReader_packetCount + 1
		else
			exitwhen true
		endif
		set i_2 = i_2 + 1
	endloop
	call IOReader_restoreNames()
endfunction

function IOReader_readPacket takes nothing returns string
	if IOReader_packetNumber == IOReader_packetCount then
		call error("IOReader: tried to read more packets than available")
	endif
	set IOReader_packetNumber = IOReader_packetNumber + 1
	return IOReader_packets[IOReader_packetNumber - 1]
endfunction

function hashtable_saveString takes hashtable this_2, integer parentKey, integer childKey, string value_2 returns nothing
	call SaveStr(this_2, parentKey, childKey, value_2)
endfunction

function Table_saveString takes integer this_2, integer parentKey, string value_2 returns nothing
	call hashtable_saveString(Table_ht, this_2, parentKey, value_2)
endfunction

function dispatch_Table_Table_Table_saveString takes integer this_2, integer parentKey, string value_2 returns nothing
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.saveString")
		else
			call error("Called Table.saveString on invalid object.")
		endif
	endif
	call Table_saveString(this_2, parentKey, value_2)
endfunction

function HashBuffer_writeString takes integer this_2, string value_2 returns nothing
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkWrite(this_2)
	set HashBuffer_stringWriteIndex[this_2] = HashBuffer_stringWriteIndex[this_2] + 1
	call dispatch_Table_Table_Table_saveString(HashBuffer_buffer[this_2], HashBuffer_stringWriteIndex[this_2], value_2)
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_writeString takes integer this_2, string value_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.writeString")
		else
			call error("Called HashBuffer.writeString on invalid object.")
		endif
	endif
	call HashBuffer_writeString(this_2, value_2)
endfunction

function getChunkPath takes string path, integer id_2 returns string
	return path + "/chunk" + int_toString(id_2) + ".txt"
endfunction

function run_submit_FileReader_MultifileIO takes integer this_2 returns nothing
	if AbstractFile_multiMode[this_806[this_2]] then
		call IOReader_load(getChunkPath(AbstractFile_path[this_806[this_2]], chunkId_624[this_2]))
	else
		call IOReader_load(AbstractFile_path[this_806[this_2]])
	endif
	if IOReader_getPacket(0) != AbstractFile_TERMINATOR[this_806[this_2]] and IOReader_canRead() then
		loop
			exitwhen  not IOReader_canRead()
			call dispatch_HashBuffer_HashBuffer_HashBuffer_writeString(AbstractFile_buffer[this_806[this_2]], IOReader_readPacket())
		endloop
		if AbstractFile_multiMode[this_806[this_2]] then
			call FileReader_readChunk(this_806[this_2], chunkId_624[this_2] + 1)
		endif
	endif
endfunction

function IOWriter_flushFile takes string path returns nothing
	call Preload(IOWriter_DATA_FOOTER)
	call PreloadGenEnd(path)
endfunction

function IOWriter_prepareWrite takes nothing returns nothing
	set IOWriter_packetNumber = 0
	call PreloadGenClear()
	call PreloadGenStart()
endfunction

function IOWriter_write takes string content returns nothing
	call Preload(content)
endfunction

function IOWriter_writePacket takes string packet returns nothing
	if IOWriter_packetNumber == PreloadIO_PACKETS_PER_FILE then
		call error("IOWriter: tried to write more packets than allowed")
	endif
	if string_length(packet) > PreloadIO_MAX_PACKET_LENGTH then
		call error("IOWriter: tried to write more than max packet length")
	endif
	call IOWriter_write(IOWriter_DATA_PADDING_1 + int_toString(IOWriter_packetNumber) + IOWriter_DATA_PADDING_2 + packet + IOWriter_DATA_PADDING_3)
	set IOWriter_packetNumber = IOWriter_packetNumber + 1
endfunction

function run_submit_FileWriter_MultifileIO takes integer this_2 returns nothing
	call IOWriter_prepareWrite()
	call IOWriter_writePacket(AbstractFile_TERMINATOR[this_803[this_2]])
	call IOWriter_flushFile(getChunkPath(AbstractFile_path[this_803[this_2]], chunkId[this_2]))
endfunction

function IOWriter_getRemainingWrites takes nothing returns integer
	return PreloadIO_PACKETS_PER_FILE - IOWriter_packetNumber
endfunction

function IOWriter_canWrite takes nothing returns boolean
	return IOWriter_getRemainingWrites() > 0
endfunction

function HashBuffer_hasString takes integer this_2 returns boolean
	return HashBuffer_stringReadIndex[this_2] < HashBuffer_stringWriteIndex[this_2]
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_hasString takes integer this_2 returns boolean
	local boolean HashBuffer_HashBuffer_hasString_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.hasString")
		else
			call error("Called HashBuffer.hasString on invalid object.")
		endif
	endif
	set HashBuffer_HashBuffer_hasString_result = HashBuffer_hasString(this_2)
	return HashBuffer_HashBuffer_hasString_result
endfunction

function hashtable_hasString takes hashtable this_2, integer parentKey, integer childKey returns boolean
	return HaveSavedString(this_2, parentKey, childKey)
endfunction

function Table_hasString takes integer this_2, integer parentKey returns boolean
	return hashtable_hasString(Table_ht, this_2, parentKey)
endfunction

function dispatch_Table_Table_Table_hasString takes integer this_2, integer parentKey returns boolean
	local boolean Table_Table_hasString_result
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.hasString")
		else
			call error("Called Table.hasString on invalid object.")
		endif
	endif
	set Table_Table_hasString_result = Table_hasString(this_2, parentKey)
	return Table_Table_hasString_result
endfunction

function hashtable_loadString takes hashtable this_2, integer parentKey, integer childKey returns string
	return LoadStr(this_2, parentKey, childKey)
endfunction

function Table_loadString takes integer this_2, integer parentKey returns string
	return hashtable_loadString(Table_ht, this_2, parentKey)
endfunction

function dispatch_Table_Table_Table_loadString takes integer this_2, integer parentKey returns string
	local string Table_Table_loadString_result
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.loadString")
		else
			call error("Called Table.loadString on invalid object.")
		endif
	endif
	set Table_Table_loadString_result = Table_loadString(this_2, parentKey)
	return Table_Table_loadString_result
endfunction

function HashBuffer_readString takes integer this_2 returns string
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkRead(this_2)
	set HashBuffer_stringReadIndex[this_2] = HashBuffer_stringReadIndex[this_2] + 1
	if  not dispatch_Table_Table_Table_hasString(HashBuffer_buffer[this_2], HashBuffer_stringReadIndex[this_2]) then
		call error("HashBuffer: trying to read non-present string at pos#" + int_toString(HashBuffer_stringReadIndex[this_2]))
	endif
	return dispatch_Table_Table_Table_loadString(HashBuffer_buffer[this_2], HashBuffer_stringReadIndex[this_2])
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_readString takes integer this_2 returns string
	local string HashBuffer_HashBuffer_readString_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.readString")
		else
			call error("Called HashBuffer.readString on invalid object.")
		endif
	endif
	set HashBuffer_HashBuffer_readString_result = HashBuffer_readString(this_2)
	return HashBuffer_HashBuffer_readString_result
endfunction

function run_submit_FileWriter_MultifileIO_1291 takes integer this_2 returns nothing
	call IOWriter_prepareWrite()
	loop
		exitwhen  not (dispatch_HashBuffer_HashBuffer_HashBuffer_hasString(AbstractFile_buffer[this_804[this_2]]) and IOWriter_canWrite())
		call IOWriter_writePacket(dispatch_HashBuffer_HashBuffer_HashBuffer_readString(AbstractFile_buffer[this_804[this_2]]))
	endloop
	if AbstractFile_multiMode[this_804[this_2]] then
		call IOWriter_flushFile(getChunkPath(AbstractFile_path[this_804[this_2]], chunkId_623[this_2]))
	else
		call IOWriter_flushFile(AbstractFile_path[this_804[this_2]])
	endif
endfunction

function dispatch_IOTask_IOTaskExecutor_IOTask_run takes integer this_2 returns nothing
	if IOTask_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling IOTask.run")
		else
			call error("Called IOTask.run on invalid object.")
		endif
	endif
	if IOTask_typeId[this_2] <= 722 then
		if IOTask_typeId[this_2] <= 721 then
			if IOTask_typeId[this_2] <= 720 then
				call run_onComplete_FileReader_MultifileIO(this_2)
			else
				call run_onComplete_FileWriter_MultifileIO(this_2)
			endif
		else
			call run_submit_FileReader_MultifileIO(this_2)
		endif
	elseif IOTask_typeId[this_2] <= 723 then
		call run_submit_FileWriter_MultifileIO(this_2)
	else
		call run_submit_FileWriter_MultifileIO_1291(this_2)
	endif
endfunction

function run_execute_AbstractIOTaskExecutor_IOTaskExecutor takes integer this_2 returns nothing
	local integer task = dispatch_LinkedList_LinkedList_LinkedList_dequeue(AbstractIOTaskExecutor_taskQueue[this_801[this_2]])
	call dispatch_IOTask_IOTaskExecutor_IOTask_run(task)
	call dispatch_IOTask_destroyIOTask(task)
endfunction

function run_execute_AbstractIOTaskExecutor_IOTaskExecutor_1275 takes integer this_2 returns nothing
	call dispatch_IOTask_IOTaskExecutor_IOTask_run(AbstractIOTaskExecutor_onCompleteTask[this_802[this_2]])
endfunction

function HashBuffer_writeInt takes integer this_2, integer value_2 returns nothing
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkWrite(this_2)
	set HashBuffer_integerWriteIndex[this_2] = HashBuffer_integerWriteIndex[this_2] + 1
	call dispatch_Table_Table_Table_saveInt(HashBuffer_buffer[this_2], HashBuffer_integerWriteIndex[this_2], value_2)
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_writeInt takes integer this_2, integer value_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.writeInt")
		else
			call error("Called HashBuffer.writeInt on invalid object.")
		endif
	endif
	call HashBuffer_writeInt(this_2, value_2)
endfunction

function hashtable_saveReal takes hashtable this_2, integer parentKey, integer childKey, real value_2 returns nothing
	call SaveReal(this_2, parentKey, childKey, value_2)
endfunction

function Table_saveReal takes integer this_2, integer parentKey, real value_2 returns nothing
	call hashtable_saveReal(Table_ht, this_2, parentKey, value_2)
endfunction

function dispatch_Table_Table_Table_saveReal takes integer this_2, integer parentKey, real value_2 returns nothing
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.saveReal")
		else
			call error("Called Table.saveReal on invalid object.")
		endif
	endif
	call Table_saveReal(this_2, parentKey, value_2)
endfunction

function HashBuffer_writeReal takes integer this_2, real value_2 returns nothing
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkWrite(this_2)
	set HashBuffer_realWriteIndex[this_2] = HashBuffer_realWriteIndex[this_2] + 1
	call dispatch_Table_Table_Table_saveReal(HashBuffer_buffer[this_2], HashBuffer_realWriteIndex[this_2], value_2)
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_writeReal takes integer this_2, real value_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.writeReal")
		else
			call error("Called HashBuffer.writeReal on invalid object.")
		endif
	endif
	call HashBuffer_writeReal(this_2, value_2)
endfunction

function OrderedStringBuffer_peekType takes integer this_2 returns integer
	local integer valueType = dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_popTypeIdentifier(this_2)
	set AbstractStringBuffer_readBuffer[this_2] = ValueType_toString(valueType) + AbstractStringBuffer_readBuffer[this_2]
	return valueType
endfunction

function dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_peekType takes integer this_2 returns integer
	local integer OrderedStringBuffer_OrderedStringBuffer_peekType_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling OrderedStringBuffer.peekType")
		else
			call error("Called OrderedStringBuffer.peekType on invalid object.")
		endif
	endif
	set OrderedStringBuffer_OrderedStringBuffer_peekType_result = OrderedStringBuffer_peekType(this_2)
	return OrderedStringBuffer_OrderedStringBuffer_peekType_result
endfunction

function dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readBoolean takes integer this_2 returns boolean
	local boolean OrderedStringBuffer_OrderedStringBuffer_readBoolean_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling OrderedStringBuffer.readBoolean")
		else
			call error("Called OrderedStringBuffer.readBoolean on invalid object.")
		endif
	endif
	set OrderedStringBuffer_OrderedStringBuffer_readBoolean_result = OrderedStringBuffer_readBoolean(this_2)
	return OrderedStringBuffer_OrderedStringBuffer_readBoolean_result
endfunction

function AbstractStringBuffer_popAll takes integer this_2 returns string
	local string value_2
	call dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_checkDataAvailable(this_2)
	set value_2 = AbstractStringBuffer_readBuffer[this_2]
	set AbstractStringBuffer_readBuffer[this_2] = ""
	call dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_nextChunk(this_2)
	return value_2
endfunction

function dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_popAll takes integer this_2 returns string
	local string StringBuffer_AbstractStringBuffer_popAll_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractStringBuffer.popAll")
		else
			call error("Called AbstractStringBuffer.popAll on invalid object.")
		endif
	endif
	set StringBuffer_AbstractStringBuffer_popAll_result = AbstractStringBuffer_popAll(this_2)
	return StringBuffer_AbstractStringBuffer_popAll_result
endfunction

function string_indexOf takes string this_2, string s returns integer
	local integer i_2 = 0
	local integer temp = string_length(this_2) - string_length(s)
	loop
		exitwhen i_2 > temp
		if string_substring(this_2, i_2, i_2 + string_length(s)) == s then
			return i_2
		endif
		set i_2 = i_2 + 1
	endloop
	return -1
endfunction

function AbstractStringBuffer_popStringUntil takes integer this_2, string terminator returns string
	local string value_2 = ""
	local integer terminatorIndex = -1
	loop
		exitwhen  not (terminatorIndex < 0)
		if string_length(value_2) > AbstractStringBuffer_MAX_BUFFER_SIZE then
			call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_fail(this_2, 3, "failed to find terminator character")
			return null
		endif
		set terminatorIndex = string_indexOf(AbstractStringBuffer_readBuffer[this_2], terminator)
		if terminatorIndex < 0 then
			set value_2 = value_2 + dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_popAll(this_2)
		else
			set value_2 = value_2 + string_substring(AbstractStringBuffer_readBuffer[this_2], 0, terminatorIndex)
			set AbstractStringBuffer_readBuffer[this_2] = string_substring_1308(AbstractStringBuffer_readBuffer[this_2], terminatorIndex + 1)
		endif
	endloop
	return value_2
endfunction

function dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_popStringUntil takes integer this_2, string terminator returns string
	local string StringBuffer_AbstractStringBuffer_popStringUntil_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractStringBuffer.popStringUntil")
		else
			call error("Called AbstractStringBuffer.popStringUntil on invalid object.")
		endif
	endif
	set StringBuffer_AbstractStringBuffer_popStringUntil_result = AbstractStringBuffer_popStringUntil(this_2, terminator)
	return StringBuffer_AbstractStringBuffer_popStringUntil_result
endfunction

function string_toInt takes string this_2 returns integer
	return S2I(this_2)
endfunction

function OrderedStringBuffer_readIntInternal takes integer this_2 returns integer
	return string_toInt(dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_popStringUntil(this_2, OrderedStringBuffer_TERMINATOR))
endfunction

function dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readIntInternal takes integer this_2 returns integer
	local integer OrderedStringBuffer_OrderedStringBuffer_readIntInternal_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling OrderedStringBuffer.readIntInternal")
		else
			call error("Called OrderedStringBuffer.readIntInternal on invalid object.")
		endif
	endif
	set OrderedStringBuffer_OrderedStringBuffer_readIntInternal_result = OrderedStringBuffer_readIntInternal(this_2)
	return OrderedStringBuffer_OrderedStringBuffer_readIntInternal_result
endfunction

function OrderedStringBuffer_readInt takes integer this_2 returns integer
	local integer cond_result
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkRead(this_2)
	if dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_checkType(this_2, 0) then
		set cond_result = dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readIntInternal(this_2)
	else
		set cond_result = 0
	endif
	return cond_result
endfunction

function dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readInt takes integer this_2 returns integer
	local integer OrderedStringBuffer_OrderedStringBuffer_readInt_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling OrderedStringBuffer.readInt")
		else
			call error("Called OrderedStringBuffer.readInt on invalid object.")
		endif
	endif
	set OrderedStringBuffer_OrderedStringBuffer_readInt_result = OrderedStringBuffer_readInt(this_2)
	return OrderedStringBuffer_OrderedStringBuffer_readInt_result
endfunction

function string_toReal takes string this_2 returns real
	return S2R(this_2)
endfunction

function OrderedStringBuffer_readReal takes integer this_2 returns real
	local real cond_result
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkRead(this_2)
	if dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_checkType(this_2, 1) then
		set cond_result = string_toReal(dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_popStringUntil(this_2, OrderedStringBuffer_TERMINATOR))
	else
		set cond_result = 0.
	endif
	return cond_result
endfunction

function dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readReal takes integer this_2 returns real
	local real OrderedStringBuffer_OrderedStringBuffer_readReal_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling OrderedStringBuffer.readReal")
		else
			call error("Called OrderedStringBuffer.readReal on invalid object.")
		endif
	endif
	set OrderedStringBuffer_OrderedStringBuffer_readReal_result = OrderedStringBuffer_readReal(this_2)
	return OrderedStringBuffer_OrderedStringBuffer_readReal_result
endfunction

function OrderedStringBuffer_readString takes integer this_2 returns string
	local string value_2
	local integer length_2
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkRead(this_2)
	set value_2 = null
	if dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_checkType(this_2, 2) then
		set length_2 = dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readIntInternal(this_2)
		if length_2 < 0 or length_2 > AbstractStringBuffer_MAX_BUFFER_SIZE then
			call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_fail(this_2, 3, "tried to read string of length " + int_toString(length_2) + ", but max is " + int_toString(AbstractStringBuffer_MAX_BUFFER_SIZE))
		else
			set value_2 = dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_popString(this_2, length_2)
		endif
	endif
	return value_2
endfunction

function dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readString takes integer this_2 returns string
	local string OrderedStringBuffer_OrderedStringBuffer_readString_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling OrderedStringBuffer.readString")
		else
			call error("Called OrderedStringBuffer.readString on invalid object.")
		endif
	endif
	set OrderedStringBuffer_OrderedStringBuffer_readString_result = OrderedStringBuffer_readString(this_2)
	return OrderedStringBuffer_OrderedStringBuffer_readString_result
endfunction

function OrderedStringBuffer_transferValueTo takes integer this_2, integer sink_2 returns nothing
	local integer valueType = dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_peekType(this_2)
	local integer temp = valueType
	if temp == 0 then
		call dispatch_HashBuffer_HashBuffer_HashBuffer_writeInt(sink_2, dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readInt(this_2))
	elseif temp == 1 then
		call dispatch_HashBuffer_HashBuffer_HashBuffer_writeReal(sink_2, dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readReal(this_2))
	elseif temp == 2 then
		call dispatch_HashBuffer_HashBuffer_HashBuffer_writeString(sink_2, dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readString(this_2))
	elseif temp == 3 then
		call dispatch_HashBuffer_HashBuffer_HashBuffer_writeBoolean(sink_2, dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readBoolean(this_2))
	endif
endfunction

function run_executeWhile_BufferAdapters takes integer this_2 returns nothing
	call OrderedStringBuffer_transferValueTo(this_790[this_2], sink[this_2])
endfunction

function alloc_ChunkElement takes nothing returns integer
	local integer this_2
	if ChunkElement_firstFree == 0 then
		if ChunkElement_maxIndex < 32768 then
			set ChunkElement_maxIndex = ChunkElement_maxIndex + 1
			set this_2 = ChunkElement_maxIndex
			set ChunkElement_typeId[this_2] = 662
		else
			call error("Out of memory: Could not create ChunkElement.")
			set this_2 = 0
		endif
	else
		set ChunkElement_firstFree = ChunkElement_firstFree - 1
		set this_2 = ChunkElement_nextFree[ChunkElement_firstFree]
		set ChunkElement_typeId[this_2] = 662
	endif
	return this_2
endfunction

function construct_ChunkElement takes integer this_2, string value_2 returns nothing
	set ChunkElement_content[this_2] = ""
	set ChunkElement_content[this_2] = value_2
endfunction

function new_ChunkElement takes string value_2 returns integer
	local integer this_2 = alloc_ChunkElement()
	call construct_ChunkElement(this_2, value_2)
	return this_2
endfunction

function AbstractStringBuffer_pushChunk takes integer this_2, string value_2 returns nothing
	call dispatch_LinkedList_LinkedList_LinkedList_push(AbstractStringBuffer_chunks[this_2], new_ChunkElement(value_2))
endfunction

function dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_pushChunk takes integer this_2, string value_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractStringBuffer.pushChunk")
		else
			call error("Called AbstractStringBuffer.pushChunk on invalid object.")
		endif
	endif
	call AbstractStringBuffer_pushChunk(this_2, value_2)
endfunction

function FileReader_readLine takes integer this_2 returns string
	return dispatch_HashBuffer_HashBuffer_HashBuffer_readString(AbstractFile_buffer[this_2])
endfunction

function dispatch_FileReader_MultifileIO_FileReader_readLine takes integer this_2 returns string
	local string MultifileIO_FileReader_readLine_result
	if AbstractFile_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling FileReader.readLine")
		else
			call error("Called FileReader.readLine on invalid object.")
		endif
	endif
	set MultifileIO_FileReader_readLine_result = FileReader_readLine(this_2)
	return MultifileIO_FileReader_readLine_result
endfunction

function run_executeWhile_BufferAdapters_1256 takes integer this_2 returns nothing
	call dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_pushChunk(this_791[this_2], dispatch_FileReader_MultifileIO_FileReader_readLine(reader_660[this_2]))
endfunction

function AbstractStringBuffer_hasChunk takes integer this_2 returns boolean
	return dispatch_LinkedList_LinkedList_LinkedList_size(AbstractStringBuffer_chunks[this_2]) > 0
endfunction

function dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_hasChunk takes integer this_2 returns boolean
	local boolean StringBuffer_AbstractStringBuffer_hasChunk_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractStringBuffer.hasChunk")
		else
			call error("Called AbstractStringBuffer.hasChunk on invalid object.")
		endif
	endif
	set StringBuffer_AbstractStringBuffer_hasChunk_result = AbstractStringBuffer_hasChunk(this_2)
	return StringBuffer_AbstractStringBuffer_hasChunk_result
endfunction

function AbstractStringBuffer_popChunk takes integer this_2 returns string
	local integer chunk
	local string value_2
	if  not dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_hasChunk(this_2) then
		call error("OrderedStringBuffer: trying to pop non-existent chunk")
	endif
	set chunk = dispatch_LinkedList_LinkedList_LinkedList_dequeue(AbstractStringBuffer_chunks[this_2])
	set value_2 = ChunkElement_content[chunk]
	call dispatch_ChunkElement_destroyChunkElement(chunk)
	return value_2
endfunction

function dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_popChunk takes integer this_2 returns string
	local string StringBuffer_AbstractStringBuffer_popChunk_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractStringBuffer.popChunk")
		else
			call error("Called AbstractStringBuffer.popChunk on invalid object.")
		endif
	endif
	set StringBuffer_AbstractStringBuffer_popChunk_result = AbstractStringBuffer_popChunk(this_2)
	return StringBuffer_AbstractStringBuffer_popChunk_result
endfunction

function HashBuffer_getStringCount takes integer this_2 returns integer
	return HashBuffer_stringWriteIndex[this_2] + 1
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_getStringCount takes integer this_2 returns integer
	local integer HashBuffer_HashBuffer_getStringCount_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.getStringCount")
		else
			call error("Called HashBuffer.getStringCount on invalid object.")
		endif
	endif
	set HashBuffer_HashBuffer_getStringCount_result = HashBuffer_getStringCount(this_2)
	return HashBuffer_HashBuffer_getStringCount_result
endfunction

function FileWriter_writeLine takes integer this_2, string value_2 returns nothing
	if ( not AbstractFile_multiMode[this_2]) and dispatch_HashBuffer_HashBuffer_HashBuffer_getStringCount(AbstractFile_buffer[this_2]) == PreloadIO_PACKETS_PER_FILE then
		call error("FileWriter: exceeded max packet count")
	endif
	call dispatch_HashBuffer_HashBuffer_HashBuffer_writeString(AbstractFile_buffer[this_2], value_2)
endfunction

function dispatch_FileWriter_MultifileIO_FileWriter_writeLine takes integer this_2, string value_2 returns nothing
	if AbstractFile_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling FileWriter.writeLine")
		else
			call error("Called FileWriter.writeLine on invalid object.")
		endif
	endif
	call FileWriter_writeLine(this_2, value_2)
endfunction

function run_executeWhile_BufferAdapters_1257 takes integer this_2 returns nothing
	call dispatch_FileWriter_MultifileIO_FileWriter_writeLine(this_792[this_2], dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_popChunk(buffer_611[this_2]))
endfunction

function sync_GamecacheBuffer_GamecacheBuffer takes integer this_2, string key returns nothing
	call gamecache_syncInt(GamecacheBuffer_gc[this_808[this_2]], GamecacheBuffer_parentKey[this_808[this_2]], key)
endfunction

function gamecache_syncReal takes gamecache this_2, string missionKey, string key returns nothing
	call SyncStoredReal(this_2, missionKey, key)
endfunction

function sync_GamecacheBuffer_GamecacheBuffer_1315 takes integer this_2, string key returns nothing
	call gamecache_syncReal(GamecacheBuffer_gc[this_809[this_2]], GamecacheBuffer_parentKey[this_809[this_2]], key)
endfunction

function gamecache_syncBoolean takes gamecache this_2, string missionKey, string key returns nothing
	call SyncStoredBoolean(this_2, missionKey, key)
endfunction

function sync_GamecacheBuffer_GamecacheBuffer_1316 takes integer this_2, string key returns nothing
	call gamecache_syncBoolean(GamecacheBuffer_gc[this_810[this_2]], GamecacheBuffer_parentKey[this_810[this_2]], key)
endfunction

function dispatch_SynchronizerFunction_GamecacheBuffer_SynchronizerFunction_sync takes integer this_2, string key returns nothing
	if SynchronizerFunction_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling SynchronizerFunction.sync")
		else
			call error("Called SynchronizerFunction.sync on invalid object.")
		endif
	endif
	if SynchronizerFunction_typeId[this_2] <= 853 then
		if SynchronizerFunction_typeId[this_2] <= 852 then
			call sync_GamecacheBuffer_GamecacheBuffer(this_2, key)
		else
			call sync_GamecacheBuffer_GamecacheBuffer_1315(this_2, key)
		endif
	else
		call sync_GamecacheBuffer_GamecacheBuffer_1316(this_2, key)
	endif
endfunction

function run_executeWhile_GamecacheBuffer_GamecacheBuffer takes integer this_2 returns nothing
	call dispatch_SynchronizerFunction_GamecacheBuffer_SynchronizerFunction_sync(synchronizer_788[this_2], GamecacheKeys_get(GamecacheBuffer_syncCounter[this_812[this_2]]))
	set GamecacheBuffer_syncCounter[this_812[this_2]] = GamecacheBuffer_syncCounter[this_812[this_2]] + 1
endfunction

function StringEncoder_pushString takes integer this_2, string value_2 returns nothing
	set StringEncoder_stringCount[this_2] = StringEncoder_stringCount[this_2] + 1
	call dispatch_HashBuffer_HashBuffer_HashBuffer_writeString(StringEncoder_buffer[this_2], value_2)
endfunction

function dispatch_StringEncoder_StringEncoder_StringEncoder_pushString takes integer this_2, string value_2 returns nothing
	if StringEncoder_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling StringEncoder.pushString")
		else
			call error("Called StringEncoder.pushString on invalid object.")
		endif
	endif
	call StringEncoder_pushString(this_2, value_2)
endfunction

function run_executeWhile_Network_Network takes integer this_2 returns nothing
	call dispatch_StringEncoder_StringEncoder_StringEncoder_pushString(Network_stringEncoder[this_814[this_2]], dispatch_HashBuffer_HashBuffer_HashBuffer_readString(Network_dataBuffer[this_814[this_2]]))
endfunction

function GamecacheBuffer_advanceWriteIndex takes integer this_2 returns nothing
	if SafetyChecks_SAFETY_CHECKS_ENABLED and GamecacheBuffer_writeIndex[this_2] + 1 >= GamecacheKeys_count then
		call error("GamecacheBuffer: trying to write out of available keys bounds")
	endif
	set GamecacheBuffer_writeIndex[this_2] = GamecacheBuffer_writeIndex[this_2] + 1
endfunction

function dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_advanceWriteIndex takes integer this_2 returns nothing
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.advanceWriteIndex")
		else
			call error("Called GamecacheBuffer.advanceWriteIndex on invalid object.")
		endif
	endif
	call GamecacheBuffer_advanceWriteIndex(this_2)
endfunction

function GamecacheBuffer_checkType takes integer this_2, integer toCheck returns nothing
	if SafetyChecks_SAFETY_CHECKS_ENABLED and GamecacheBuffer_bufferType[this_2] != toCheck then
		call error("GamecacheBuffer: invalid buffer usage - mismatched type")
	endif
endfunction

function dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_checkType takes integer this_2, integer toCheck returns nothing
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.checkType")
		else
			call error("Called GamecacheBuffer.checkType on invalid object.")
		endif
	endif
	call GamecacheBuffer_checkType(this_2, toCheck)
endfunction

function GamecacheBuffer_writeInt takes integer this_2, integer value_2 returns nothing
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_advanceWriteIndex(this_2)
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_checkType(this_2, 0)
	call gamecache_saveInt(GamecacheBuffer_gc[this_2], GamecacheBuffer_parentKey[this_2], GamecacheKeys_get(GamecacheBuffer_writeIndex[this_2]), value_2)
endfunction

function dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_writeInt takes integer this_2, integer value_2 returns nothing
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.writeInt")
		else
			call error("Called GamecacheBuffer.writeInt on invalid object.")
		endif
	endif
	call GamecacheBuffer_writeInt(this_2, value_2)
endfunction

function HashBuffer_readIntUnsafe takes integer this_2 returns integer
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkRead(this_2)
	set HashBuffer_integerReadIndex[this_2] = HashBuffer_integerReadIndex[this_2] + 1
	if SafetyChecks_SAFETY_CHECKS_ENABLED and ( not dispatch_Table_Table_Table_hasInt(HashBuffer_buffer[this_2], HashBuffer_integerReadIndex[this_2])) then
		call error("HashBuffer: trying to read non-present int at pos#" + int_toString(HashBuffer_integerReadIndex[this_2]))
	endif
	return dispatch_Table_Table_Table_loadInt(HashBuffer_buffer[this_2], HashBuffer_integerReadIndex[this_2])
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_readIntUnsafe takes integer this_2 returns integer
	local integer HashBuffer_HashBuffer_readIntUnsafe_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.readIntUnsafe")
		else
			call error("Called HashBuffer.readIntUnsafe on invalid object.")
		endif
	endif
	set HashBuffer_HashBuffer_readIntUnsafe_result = HashBuffer_readIntUnsafe(this_2)
	return HashBuffer_HashBuffer_readIntUnsafe_result
endfunction

function run_executeWhile_Network_Network_1260 takes integer this_2 returns nothing
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_writeInt(Network_gcIntBuffer[this_816[this_2]], dispatch_HashBuffer_HashBuffer_HashBuffer_readIntUnsafe(Network_dataBuffer[this_816[this_2]]))
	set Network_counters_ints[this_816[this_2]] = Network_counters_ints[this_816[this_2]] + 1
endfunction

function GamecacheBuffer_advanceReadIndex takes integer this_2 returns nothing
	if SafetyChecks_SAFETY_CHECKS_ENABLED and GamecacheBuffer_readIndex[this_2] + 1 >= GamecacheKeys_count then
		call error("GamecacheBuffer: trying to read out of available keys bounds")
	endif
	set GamecacheBuffer_readIndex[this_2] = GamecacheBuffer_readIndex[this_2] + 1
endfunction

function dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_advanceReadIndex takes integer this_2 returns nothing
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.advanceReadIndex")
		else
			call error("Called GamecacheBuffer.advanceReadIndex on invalid object.")
		endif
	endif
	call GamecacheBuffer_advanceReadIndex(this_2)
endfunction

function gamecache_hasInt takes gamecache this_2, string missionKey, string key returns boolean
	return HaveStoredInteger(this_2, missionKey, key)
endfunction

function GamecacheBuffer_readInt takes integer this_2 returns integer
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_advanceReadIndex(this_2)
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_checkType(this_2, 0)
	if  not gamecache_hasInt(GamecacheBuffer_gc[this_2], GamecacheBuffer_parentKey[this_2], GamecacheKeys_get(GamecacheBuffer_readIndex[this_2])) then
		call error("GamecacheBuffer: trying to read non-present int #" + int_toString(GamecacheBuffer_readIndex[this_2]))
	endif
	return gamecache_loadInt(GamecacheBuffer_gc[this_2], GamecacheBuffer_parentKey[this_2], GamecacheKeys_get(GamecacheBuffer_readIndex[this_2]))
endfunction

function dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_readInt takes integer this_2 returns integer
	local integer GamecacheBuffer_GamecacheBuffer_readInt_result
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.readInt")
		else
			call error("Called GamecacheBuffer.readInt on invalid object.")
		endif
	endif
	set GamecacheBuffer_GamecacheBuffer_readInt_result = GamecacheBuffer_readInt(this_2)
	return GamecacheBuffer_GamecacheBuffer_readInt_result
endfunction

function run_executeWhile_Network_Network_1261 takes integer this_2 returns nothing
	call dispatch_HashBuffer_HashBuffer_HashBuffer_writeInt(Network_dataBuffer[this_818[this_2]], dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_readInt(Network_gcIntBuffer[this_818[this_2]]))
	set Network_counters_ints[this_818[this_2]] = Network_counters_ints[this_818[this_2]] + 1
endfunction

function gamecache_saveReal takes gamecache this_2, string missionKey, string key, real value_2 returns nothing
	call StoreReal(this_2, missionKey, key, value_2)
endfunction

function GamecacheBuffer_writeReal takes integer this_2, real value_2 returns nothing
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_advanceWriteIndex(this_2)
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_checkType(this_2, 1)
	call gamecache_saveReal(GamecacheBuffer_gc[this_2], GamecacheBuffer_parentKey[this_2], GamecacheKeys_get(GamecacheBuffer_writeIndex[this_2]), value_2)
endfunction

function dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_writeReal takes integer this_2, real value_2 returns nothing
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.writeReal")
		else
			call error("Called GamecacheBuffer.writeReal on invalid object.")
		endif
	endif
	call GamecacheBuffer_writeReal(this_2, value_2)
endfunction

function hashtable_hasReal takes hashtable this_2, integer parentKey, integer childKey returns boolean
	return HaveSavedReal(this_2, parentKey, childKey)
endfunction

function Table_hasReal takes integer this_2, integer parentKey returns boolean
	return hashtable_hasReal(Table_ht, this_2, parentKey)
endfunction

function dispatch_Table_Table_Table_hasReal takes integer this_2, integer parentKey returns boolean
	local boolean Table_Table_hasReal_result
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.hasReal")
		else
			call error("Called Table.hasReal on invalid object.")
		endif
	endif
	set Table_Table_hasReal_result = Table_hasReal(this_2, parentKey)
	return Table_Table_hasReal_result
endfunction

function hashtable_loadReal takes hashtable this_2, integer parentKey, integer childKey returns real
	return LoadReal(this_2, parentKey, childKey)
endfunction

function Table_loadReal takes integer this_2, integer parentKey returns real
	return hashtable_loadReal(Table_ht, this_2, parentKey)
endfunction

function dispatch_Table_Table_Table_loadReal takes integer this_2, integer parentKey returns real
	local real Table_Table_loadReal_result
	if Table_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Table.loadReal")
		else
			call error("Called Table.loadReal on invalid object.")
		endif
	endif
	set Table_Table_loadReal_result = Table_loadReal(this_2, parentKey)
	return Table_Table_loadReal_result
endfunction

function HashBuffer_readRealUnsafe takes integer this_2 returns real
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkRead(this_2)
	set HashBuffer_realReadIndex[this_2] = HashBuffer_realReadIndex[this_2] + 1
	if SafetyChecks_SAFETY_CHECKS_ENABLED and ( not dispatch_Table_Table_Table_hasReal(HashBuffer_buffer[this_2], HashBuffer_realReadIndex[this_2])) then
		call error("HashBuffer: trying to read non-present real at pos#" + int_toString(HashBuffer_realReadIndex[this_2]))
	endif
	return dispatch_Table_Table_Table_loadReal(HashBuffer_buffer[this_2], HashBuffer_realReadIndex[this_2])
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_readRealUnsafe takes integer this_2 returns real
	local real HashBuffer_HashBuffer_readRealUnsafe_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.readRealUnsafe")
		else
			call error("Called HashBuffer.readRealUnsafe on invalid object.")
		endif
	endif
	set HashBuffer_HashBuffer_readRealUnsafe_result = HashBuffer_readRealUnsafe(this_2)
	return HashBuffer_HashBuffer_readRealUnsafe_result
endfunction

function run_executeWhile_Network_Network_1262 takes integer this_2 returns nothing
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_writeReal(Network_gcRealBuffer[this_820[this_2]], dispatch_HashBuffer_HashBuffer_HashBuffer_readRealUnsafe(Network_dataBuffer[this_820[this_2]]))
	set Network_counters_reals[this_820[this_2]] = Network_counters_reals[this_820[this_2]] + 1
endfunction

function gamecache_hasReal takes gamecache this_2, string missionKey, string key returns boolean
	return HaveStoredReal(this_2, missionKey, key)
endfunction

function gamecache_loadReal takes gamecache this_2, string missionKey, string key returns real
	return GetStoredReal(this_2, missionKey, key)
endfunction

function GamecacheBuffer_readReal takes integer this_2 returns real
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_advanceReadIndex(this_2)
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_checkType(this_2, 1)
	if  not gamecache_hasReal(GamecacheBuffer_gc[this_2], GamecacheBuffer_parentKey[this_2], GamecacheKeys_get(GamecacheBuffer_readIndex[this_2])) then
		call error("GamecacheBuffer: trying to read non-present real #" + int_toString(GamecacheBuffer_readIndex[this_2]))
	endif
	return gamecache_loadReal(GamecacheBuffer_gc[this_2], GamecacheBuffer_parentKey[this_2], GamecacheKeys_get(GamecacheBuffer_readIndex[this_2]))
endfunction

function dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_readReal takes integer this_2 returns real
	local real GamecacheBuffer_GamecacheBuffer_readReal_result
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.readReal")
		else
			call error("Called GamecacheBuffer.readReal on invalid object.")
		endif
	endif
	set GamecacheBuffer_GamecacheBuffer_readReal_result = GamecacheBuffer_readReal(this_2)
	return GamecacheBuffer_GamecacheBuffer_readReal_result
endfunction

function run_executeWhile_Network_Network_1263 takes integer this_2 returns nothing
	call dispatch_HashBuffer_HashBuffer_HashBuffer_writeReal(Network_dataBuffer[this_822[this_2]], dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_readReal(Network_gcRealBuffer[this_822[this_2]]))
	set Network_counters_reals[this_822[this_2]] = Network_counters_reals[this_822[this_2]] + 1
endfunction

function gamecache_saveBoolean takes gamecache this_2, string missionKey, string key, boolean value_2 returns nothing
	call StoreBoolean(this_2, missionKey, key, value_2)
endfunction

function GamecacheBuffer_writeBoolean takes integer this_2, boolean value_2 returns nothing
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_advanceWriteIndex(this_2)
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_checkType(this_2, 2)
	call gamecache_saveBoolean(GamecacheBuffer_gc[this_2], GamecacheBuffer_parentKey[this_2], GamecacheKeys_get(GamecacheBuffer_writeIndex[this_2]), value_2)
endfunction

function dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_writeBoolean takes integer this_2, boolean value_2 returns nothing
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.writeBoolean")
		else
			call error("Called GamecacheBuffer.writeBoolean on invalid object.")
		endif
	endif
	call GamecacheBuffer_writeBoolean(this_2, value_2)
endfunction

function HashBuffer_readBooleanUnsafe takes integer this_2 returns boolean
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkRead(this_2)
	set HashBuffer_booleanReadIndex[this_2] = HashBuffer_booleanReadIndex[this_2] + 1
	if SafetyChecks_SAFETY_CHECKS_ENABLED and ( not dispatch_Table_Table_Table_hasBoolean(HashBuffer_buffer[this_2], HashBuffer_booleanReadIndex[this_2])) then
		call error("HashBuffer: trying to read non-present boolean at pos#" + int_toString(HashBuffer_booleanReadIndex[this_2]))
	endif
	return dispatch_Table_Table_Table_loadBoolean(HashBuffer_buffer[this_2], HashBuffer_booleanReadIndex[this_2])
endfunction

function dispatch_HashBuffer_HashBuffer_HashBuffer_readBooleanUnsafe takes integer this_2 returns boolean
	local boolean HashBuffer_HashBuffer_readBooleanUnsafe_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling HashBuffer.readBooleanUnsafe")
		else
			call error("Called HashBuffer.readBooleanUnsafe on invalid object.")
		endif
	endif
	set HashBuffer_HashBuffer_readBooleanUnsafe_result = HashBuffer_readBooleanUnsafe(this_2)
	return HashBuffer_HashBuffer_readBooleanUnsafe_result
endfunction

function run_executeWhile_Network_Network_1264 takes integer this_2 returns nothing
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_writeBoolean(Network_gcBooleanBuffer[this_824[this_2]], dispatch_HashBuffer_HashBuffer_HashBuffer_readBooleanUnsafe(Network_dataBuffer[this_824[this_2]]))
	set Network_counters_booleans[this_824[this_2]] = Network_counters_booleans[this_824[this_2]] + 1
endfunction

function gamecache_hasBoolean takes gamecache this_2, string missionKey, string key returns boolean
	return HaveStoredBoolean(this_2, missionKey, key)
endfunction

function gamecache_loadBoolean takes gamecache this_2, string missionKey, string key returns boolean
	return GetStoredBoolean(this_2, missionKey, key)
endfunction

function GamecacheBuffer_readBoolean takes integer this_2 returns boolean
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_advanceReadIndex(this_2)
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_checkType(this_2, 2)
	if  not gamecache_hasBoolean(GamecacheBuffer_gc[this_2], GamecacheBuffer_parentKey[this_2], GamecacheKeys_get(GamecacheBuffer_readIndex[this_2])) then
		call error("GamecacheBuffer: trying to read non-present bool #" + int_toString(GamecacheBuffer_readIndex[this_2]))
	endif
	return gamecache_loadBoolean(GamecacheBuffer_gc[this_2], GamecacheBuffer_parentKey[this_2], GamecacheKeys_get(GamecacheBuffer_readIndex[this_2]))
endfunction

function dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_readBoolean takes integer this_2 returns boolean
	local boolean GamecacheBuffer_GamecacheBuffer_readBoolean_result
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.readBoolean")
		else
			call error("Called GamecacheBuffer.readBoolean on invalid object.")
		endif
	endif
	set GamecacheBuffer_GamecacheBuffer_readBoolean_result = GamecacheBuffer_readBoolean(this_2)
	return GamecacheBuffer_GamecacheBuffer_readBoolean_result
endfunction

function run_executeWhile_Network_Network_1265 takes integer this_2 returns nothing
	call dispatch_HashBuffer_HashBuffer_HashBuffer_writeBoolean(Network_dataBuffer[this_826[this_2]], dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_readBoolean(Network_gcBooleanBuffer[this_826[this_2]]))
	set Network_counters_booleans[this_826[this_2]] = Network_counters_booleans[this_826[this_2]] + 1
endfunction

function StringEncoder_popInt takes integer this_2 returns integer
	set StringEncoder_asciiIntCount[this_2] = StringEncoder_asciiIntCount[this_2] - 1
	return dispatch_HashBuffer_HashBuffer_HashBuffer_readIntUnsafe(StringEncoder_buffer[this_2])
endfunction

function dispatch_StringEncoder_StringEncoder_StringEncoder_popInt takes integer this_2 returns integer
	local integer StringEncoder_StringEncoder_popInt_result
	if StringEncoder_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling StringEncoder.popInt")
		else
			call error("Called StringEncoder.popInt on invalid object.")
		endif
	endif
	set StringEncoder_StringEncoder_popInt_result = StringEncoder_popInt(this_2)
	return StringEncoder_StringEncoder_popInt_result
endfunction

function run_executeWhile_Network_Network_1266 takes integer this_2 returns nothing
	call dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_writeInt(Network_gcStringBuffer[this_828[this_2]], dispatch_StringEncoder_StringEncoder_StringEncoder_popInt(Network_stringEncoder[this_828[this_2]]))
	set Network_counters_asciiInts[this_828[this_2]] = Network_counters_asciiInts[this_828[this_2]] + 1
endfunction

function StringEncoder_pushInt takes integer this_2, integer value_2 returns nothing
	set StringEncoder_asciiIntCount[this_2] = StringEncoder_asciiIntCount[this_2] + 1
	call dispatch_HashBuffer_HashBuffer_HashBuffer_writeInt(StringEncoder_buffer[this_2], value_2)
endfunction

function dispatch_StringEncoder_StringEncoder_StringEncoder_pushInt takes integer this_2, integer value_2 returns nothing
	if StringEncoder_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling StringEncoder.pushInt")
		else
			call error("Called StringEncoder.pushInt on invalid object.")
		endif
	endif
	call StringEncoder_pushInt(this_2, value_2)
endfunction

function run_executeWhile_Network_Network_1267 takes integer this_2 returns nothing
	call dispatch_StringEncoder_StringEncoder_StringEncoder_pushInt(Network_stringEncoder[this_830[this_2]], dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_readInt(Network_gcStringBuffer[this_830[this_2]]))
	set Network_counters_asciiInts[this_830[this_2]] = Network_counters_asciiInts[this_830[this_2]] + 1
endfunction

function StringEncoder_popString takes integer this_2 returns string
	set StringEncoder_stringCount[this_2] = StringEncoder_stringCount[this_2] - 1
	return dispatch_HashBuffer_HashBuffer_HashBuffer_readString(StringEncoder_buffer[this_2])
endfunction

function dispatch_StringEncoder_StringEncoder_StringEncoder_popString takes integer this_2 returns string
	local string StringEncoder_StringEncoder_popString_result
	if StringEncoder_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling StringEncoder.popString")
		else
			call error("Called StringEncoder.popString on invalid object.")
		endif
	endif
	set StringEncoder_StringEncoder_popString_result = StringEncoder_popString(this_2)
	return StringEncoder_StringEncoder_popString_result
endfunction

function run_executeWhile_Network_Network_1268 takes integer this_2 returns nothing
	call dispatch_HashBuffer_HashBuffer_HashBuffer_writeString(Network_dataBuffer[this_834[this_2]], dispatch_StringEncoder_StringEncoder_StringEncoder_popString(Network_stringEncoder[this_834[this_2]]))
endfunction

function boolean_toInt takes boolean this_2 returns integer
	local integer cond_result
	if this_2 then
		set cond_result = 1
	else
		set cond_result = 0
	endif
	return cond_result
endfunction

function AbstractStringBuffer_flushBuffer takes integer this_2 returns nothing
	call dispatch_LinkedList_LinkedList_LinkedList_push(AbstractStringBuffer_chunks[this_2], new_ChunkElement(AbstractStringBuffer_writeBuffer[this_2]))
	set AbstractStringBuffer_writeBuffer[this_2] = ""
endfunction

function dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_flushBuffer takes integer this_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractStringBuffer.flushBuffer")
		else
			call error("Called AbstractStringBuffer.flushBuffer on invalid object.")
		endif
	endif
	call AbstractStringBuffer_flushBuffer(this_2)
endfunction

function AbstractStringBuffer_pushString takes integer this_2, string data returns nothing
	local string remaining = data
	local integer overflow
	local integer toAppend
	if string_length(data) > AbstractStringBuffer_MAX_BUFFER_SIZE then
		call error("AbstractStringBuffer: trying to push a string above MAX_BUFFER_SIZE")
	endif
	set overflow = string_length(data) + string_length(AbstractStringBuffer_writeBuffer[this_2]) - AbstractStringBuffer_maxBufferSize[this_2]
	loop
		exitwhen  not (overflow > 0)
		set toAppend = min_2(string_length(remaining), AbstractStringBuffer_maxBufferSize[this_2] - string_length(AbstractStringBuffer_writeBuffer[this_2]))
		set AbstractStringBuffer_writeBuffer[this_2] = AbstractStringBuffer_writeBuffer[this_2] + string_substring(remaining, 0, toAppend)
		set remaining = string_substring_1308(remaining, toAppend)
		set overflow = overflow - AbstractStringBuffer_maxBufferSize[this_2]
		call dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_flushBuffer(this_2)
	endloop
	set AbstractStringBuffer_writeBuffer[this_2] = AbstractStringBuffer_writeBuffer[this_2] + remaining
endfunction

function dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_pushString takes integer this_2, string data returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractStringBuffer.pushString")
		else
			call error("Called AbstractStringBuffer.pushString on invalid object.")
		endif
	endif
	call AbstractStringBuffer_pushString(this_2, data)
endfunction

function OrderedStringBuffer_pushTypeIdentifier takes integer this_2, integer valueType returns nothing
	call dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_pushString(this_2, ValueType_toString(valueType))
endfunction

function dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_pushTypeIdentifier takes integer this_2, integer valueType returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling OrderedStringBuffer.pushTypeIdentifier")
		else
			call error("Called OrderedStringBuffer.pushTypeIdentifier on invalid object.")
		endif
	endif
	call OrderedStringBuffer_pushTypeIdentifier(this_2, valueType)
endfunction

function OrderedStringBuffer_writeBoolean takes integer this_2, boolean value_2 returns nothing
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkWrite(this_2)
	call dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_pushTypeIdentifier(this_2, 3)
	call dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_pushString(this_2, int_toString(boolean_toInt(value_2)))
endfunction

function dispatch_Buffer_BufferInterface_Buffer_writeBoolean takes integer this_2, boolean value_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Buffer.writeBoolean")
		else
			call error("Called Buffer.writeBoolean on invalid object.")
		endif
	endif
	if Buffer_typeId[this_2] <= 644 then
		call OrderedStringBuffer_writeBoolean(this_2, value_2)
	else
		call HashBuffer_writeBoolean(this_2, value_2)
	endif
endfunction

function OrderedStringBuffer_pushTerminator takes integer this_2 returns nothing
	call dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_pushString(this_2, OrderedStringBuffer_TERMINATOR)
endfunction

function dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_pushTerminator takes integer this_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling OrderedStringBuffer.pushTerminator")
		else
			call error("Called OrderedStringBuffer.pushTerminator on invalid object.")
		endif
	endif
	call OrderedStringBuffer_pushTerminator(this_2)
endfunction

function OrderedStringBuffer_writeInt takes integer this_2, integer value_2 returns nothing
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkWrite(this_2)
	call dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_pushTypeIdentifier(this_2, 0)
	call dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_pushString(this_2, int_toString(value_2))
	call dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_pushTerminator(this_2)
endfunction

function dispatch_Buffer_BufferInterface_Buffer_writeInt takes integer this_2, integer value_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Buffer.writeInt")
		else
			call error("Called Buffer.writeInt on invalid object.")
		endif
	endif
	if Buffer_typeId[this_2] <= 644 then
		call OrderedStringBuffer_writeInt(this_2, value_2)
	else
		call HashBuffer_writeInt(this_2, value_2)
	endif
endfunction

function real_toString takes real this_2 returns string
	return R2S(this_2)
endfunction

function OrderedStringBuffer_writeReal takes integer this_2, real value_2 returns nothing
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkWrite(this_2)
	call dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_pushTypeIdentifier(this_2, 1)
	call dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_pushString(this_2, real_toString(value_2))
	call dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_pushTerminator(this_2)
endfunction

function dispatch_Buffer_BufferInterface_Buffer_writeReal takes integer this_2, real value_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Buffer.writeReal")
		else
			call error("Called Buffer.writeReal on invalid object.")
		endif
	endif
	if Buffer_typeId[this_2] <= 644 then
		call OrderedStringBuffer_writeReal(this_2, value_2)
	else
		call HashBuffer_writeReal(this_2, value_2)
	endif
endfunction

function OrderedStringBuffer_writeString takes integer this_2, string value_2 returns nothing
	if string_length(value_2) > AbstractStringBuffer_MAX_BUFFER_SIZE then
		call error("OrderedStringBuffer: trying to write string above MAX_BUFFER_SIZE")
	endif
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkWrite(this_2)
	call dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_pushTypeIdentifier(this_2, 2)
	call dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_pushString(this_2, int_toString(string_length(value_2)))
	call dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_pushTerminator(this_2)
	call dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_pushString(this_2, value_2)
endfunction

function dispatch_Buffer_BufferInterface_Buffer_writeString takes integer this_2, string value_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Buffer.writeString")
		else
			call error("Called Buffer.writeString on invalid object.")
		endif
	endif
	if Buffer_typeId[this_2] <= 644 then
		call OrderedStringBuffer_writeString(this_2, value_2)
	else
		call HashBuffer_writeString(this_2, value_2)
	endif
endfunction

function OrderedStringBuffer_transferValueTo_333 takes integer this_2, integer sink_2 returns nothing
	local integer valueType = dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_peekType(this_2)
	local integer temp = valueType
	if temp == 0 then
		call dispatch_Buffer_BufferInterface_Buffer_writeInt(sink_2, dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readInt(this_2))
	elseif temp == 1 then
		call dispatch_Buffer_BufferInterface_Buffer_writeReal(sink_2, dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readReal(this_2))
	elseif temp == 2 then
		call dispatch_Buffer_BufferInterface_Buffer_writeString(sink_2, dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readString(this_2))
	elseif temp == 3 then
		call dispatch_Buffer_BufferInterface_Buffer_writeBoolean(sink_2, dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_readBoolean(this_2))
	endif
endfunction

function dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_transferValueTo takes integer this_2, integer sink_2 returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling OrderedStringBuffer.transferValueTo")
		else
			call error("Called OrderedStringBuffer.transferValueTo on invalid object.")
		endif
	endif
	call OrderedStringBuffer_transferValueTo_333(this_2, sink_2)
endfunction

function run_executeWhile_OrderedStringBuffer_OrderedStringBuffer takes integer this_2 returns nothing
	call dispatch_OrderedStringBuffer_OrderedStringBuffer_OrderedStringBuffer_transferValueTo(this_794[this_2], buffer_612[this_2])
endfunction

function string_toLowerCase takes string this_2 returns string
	return StringCase(this_2, false)
endfunction

function string_toUpperCase takes string this_2 returns string
	return StringCase(this_2, true)
endfunction

function getIndexFromChar takes string c returns integer
	local integer hash
	if (string_toUpperCase(c) == c and string_toLowerCase(c) != c) or c == "/" then
		set hash = string_getHash("00" + c)
	else
		set hash = string_getHash(c)
	endif
	if SafetyChecks_SAFETY_CHECKS_ENABLED and ( not dispatch_HashMap_HashMap_HashMap_has(StringEncoder_alphabetLookup, hash)) then
		call error("Network: trying to get index from invalid character '" + c + "'")
	endif
	return dispatch_HashMap_HashMap_HashMap_get(StringEncoder_alphabetLookup, hash)
endfunction

function string_charAt takes string this_2, integer index returns string
	return SubString(this_2, index, index + 1)
endfunction

function run_executeWhile_StringEncoder_StringEncoder takes integer this_2 returns nothing
	call dispatch_HashBuffer_HashBuffer_HashBuffer_writeInt(StringEncoder_buffer[this_836[this_2]], getIndexFromChar(string_charAt(value[this_2], StringEncoder_counter[this_836[this_2]])))
	set StringEncoder_counter[this_836[this_2]] = StringEncoder_counter[this_836[this_2]] + 1
	set StringEncoder_asciiIntCount[this_836[this_2]] = StringEncoder_asciiIntCount[this_836[this_2]] + 1
endfunction

function getCharFromIndex takes integer i_2 returns string
	if i_2 > StringEncoder_STRING_ALPHABET_LENGTH then
		call error("Network: trying to get invalid character from index '" + int_toString(i_2) + "'")
	endif
	return string_charAt(StringEncoder_STRING_ALPHABET, i_2)
endfunction

function run_executeWhile_StringEncoder_StringEncoder_1271 takes integer this_2 returns nothing
	set StringEncoder_currentValue[this_838[this_2]] = StringEncoder_currentValue[this_838[this_2]] + getCharFromIndex(dispatch_HashBuffer_HashBuffer_HashBuffer_readIntUnsafe(StringEncoder_buffer[this_838[this_2]]))
	set StringEncoder_counter[this_838[this_2]] = StringEncoder_counter[this_838[this_2]] + 1
	set StringEncoder_asciiIntCount[this_838[this_2]] = StringEncoder_asciiIntCount[this_838[this_2]] - 1
endfunction

function alloc_LimitedExecuteAction_executeWhile_StringEncoder_StringEncoder takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 766
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_StringEncoder_StringEncoder.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 766
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_StringEncoder_StringEncoder takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 786
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_StringEncoder_StringEncoder.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 786
	endif
	return this_2
endfunction

function StringEncoder_encodeString takes integer this_2, string value_2 returns nothing
	local integer length_2 = string_length(value_2)
	local integer clVar
	local integer clVar_2
	local integer temp
	local integer temp_2
	set StringEncoder_counter[this_2] = 0
	call dispatch_HashBuffer_HashBuffer_HashBuffer_writeInt(StringEncoder_buffer[this_2], length_2)
	set StringEncoder_asciiIntCount[this_2] = StringEncoder_asciiIntCount[this_2] + 1
	set temp = NetworkConfig_CHARS_PER_ENCODE_DECODE
	set clVar = alloc_LimitedExecuteCondition_executeWhile_StringEncoder_StringEncoder()
	set this_835[clVar] = this_2
	set length[clVar] = length_2
	set temp_2 = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_StringEncoder_StringEncoder()
	set this_836[clVar_2] = this_2
	set value[clVar_2] = value_2
	call executeWhile(temp, temp_2, clVar_2)
endfunction

function dispatch_StringEncoder_StringEncoder_StringEncoder_encodeString takes integer this_2, string value_2 returns nothing
	if StringEncoder_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling StringEncoder.encodeString")
		else
			call error("Called StringEncoder.encodeString on invalid object.")
		endif
	endif
	call StringEncoder_encodeString(this_2, value_2)
endfunction

function run_executeWhile_StringEncoder_StringEncoder_1272 takes integer this_2 returns nothing
	call dispatch_StringEncoder_StringEncoder_StringEncoder_encodeString(this_840[this_2], dispatch_StringEncoder_StringEncoder_StringEncoder_popString(this_840[this_2]))
endfunction

function alloc_LimitedExecuteAction_executeWhile_StringEncoder_StringEncoder_521 takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 767
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_StringEncoder_StringEncoder.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 767
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_StringEncoder_StringEncoder_538 takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 787
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_StringEncoder_StringEncoder.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 787
	endif
	return this_2
endfunction

function StringEncoder_decodeString takes integer this_2 returns string
	local integer length_2
	local integer clVar
	local integer clVar_2
	local integer temp
	local integer temp_2
	set StringEncoder_currentValue[this_2] = ""
	set length_2 = dispatch_HashBuffer_HashBuffer_HashBuffer_readIntUnsafe(StringEncoder_buffer[this_2])
	set StringEncoder_asciiIntCount[this_2] = StringEncoder_asciiIntCount[this_2] - 1
	set StringEncoder_counter[this_2] = 0
	set temp = NetworkConfig_CHARS_PER_ENCODE_DECODE
	set clVar = alloc_LimitedExecuteCondition_executeWhile_StringEncoder_StringEncoder_538()
	set this_837[clVar] = this_2
	set length_654[clVar] = length_2
	set temp_2 = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_StringEncoder_StringEncoder_521()
	set this_838[clVar_2] = this_2
	call executeWhile(temp, temp_2, clVar_2)
	return StringEncoder_currentValue[this_2]
endfunction

function dispatch_StringEncoder_StringEncoder_StringEncoder_decodeString takes integer this_2 returns string
	local string StringEncoder_StringEncoder_decodeString_result
	if StringEncoder_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling StringEncoder.decodeString")
		else
			call error("Called StringEncoder.decodeString on invalid object.")
		endif
	endif
	set StringEncoder_StringEncoder_decodeString_result = StringEncoder_decodeString(this_2)
	return StringEncoder_StringEncoder_decodeString_result
endfunction

function run_executeWhile_StringEncoder_StringEncoder_1273 takes integer this_2 returns nothing
	call dispatch_StringEncoder_StringEncoder_StringEncoder_pushString(this_842[this_2], dispatch_StringEncoder_StringEncoder_StringEncoder_decodeString(this_842[this_2]))
endfunction

function dispatch_LimitedExecuteAction_Execute_LimitedExecuteAction_run takes integer this_2 returns nothing
	if LimitedExecuteAction_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LimitedExecuteAction.run")
		else
			call error("Called LimitedExecuteAction.run on invalid object.")
		endif
	endif
	if LimitedExecuteAction_typeId[this_2] <= 760 then
		if LimitedExecuteAction_typeId[this_2] <= 755 then
			if LimitedExecuteAction_typeId[this_2] <= 753 then
				if LimitedExecuteAction_typeId[this_2] <= 752 then
					if LimitedExecuteAction_typeId[this_2] <= 751 then
						call run_executeWhile_BufferAdapters(this_2)
					else
						call run_executeWhile_BufferAdapters_1256(this_2)
					endif
				else
					call run_executeWhile_BufferAdapters_1257(this_2)
				endif
			elseif LimitedExecuteAction_typeId[this_2] <= 754 then
				call run_executeWhile_GamecacheBuffer_GamecacheBuffer(this_2)
			else
				call run_executeWhile_Network_Network(this_2)
			endif
		elseif LimitedExecuteAction_typeId[this_2] <= 758 then
			if LimitedExecuteAction_typeId[this_2] <= 757 then
				if LimitedExecuteAction_typeId[this_2] <= 756 then
					call run_executeWhile_Network_Network_1260(this_2)
				else
					call run_executeWhile_Network_Network_1261(this_2)
				endif
			else
				call run_executeWhile_Network_Network_1262(this_2)
			endif
		elseif LimitedExecuteAction_typeId[this_2] <= 759 then
			call run_executeWhile_Network_Network_1263(this_2)
		else
			call run_executeWhile_Network_Network_1264(this_2)
		endif
	elseif LimitedExecuteAction_typeId[this_2] <= 765 then
		if LimitedExecuteAction_typeId[this_2] <= 763 then
			if LimitedExecuteAction_typeId[this_2] <= 762 then
				if LimitedExecuteAction_typeId[this_2] <= 761 then
					call run_executeWhile_Network_Network_1265(this_2)
				else
					call run_executeWhile_Network_Network_1266(this_2)
				endif
			else
				call run_executeWhile_Network_Network_1267(this_2)
			endif
		elseif LimitedExecuteAction_typeId[this_2] <= 764 then
			call run_executeWhile_Network_Network_1268(this_2)
		else
			call run_executeWhile_OrderedStringBuffer_OrderedStringBuffer(this_2)
		endif
	elseif LimitedExecuteAction_typeId[this_2] <= 767 then
		if LimitedExecuteAction_typeId[this_2] <= 766 then
			call run_executeWhile_StringEncoder_StringEncoder(this_2)
		else
			call run_executeWhile_StringEncoder_StringEncoder_1271(this_2)
		endif
	elseif LimitedExecuteAction_typeId[this_2] <= 768 then
		call run_executeWhile_StringEncoder_StringEncoder_1272(this_2)
	else
		call run_executeWhile_StringEncoder_StringEncoder_1273(this_2)
	endif
endfunction

function AbstractStringBuffer_canRead takes integer this_2 returns boolean
	return dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_isDataAvailable(this_2)
endfunction

function dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_canRead takes integer this_2 returns boolean
	local boolean StringBuffer_AbstractStringBuffer_canRead_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractStringBuffer.canRead")
		else
			call error("Called AbstractStringBuffer.canRead on invalid object.")
		endif
	endif
	set StringBuffer_AbstractStringBuffer_canRead_result = AbstractStringBuffer_canRead(this_2)
	return StringBuffer_AbstractStringBuffer_canRead_result
endfunction

function check_executeWhile_BufferAdapters takes integer this_2 returns boolean
	return dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_canRead(this[this_2])
endfunction

function FileReader_canRead takes integer this_2 returns boolean
	return dispatch_HashBuffer_HashBuffer_HashBuffer_hasString(AbstractFile_buffer[this_2])
endfunction

function dispatch_FileReader_MultifileIO_FileReader_canRead takes integer this_2 returns boolean
	local boolean MultifileIO_FileReader_canRead_result
	if AbstractFile_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling FileReader.canRead")
		else
			call error("Called FileReader.canRead on invalid object.")
		endif
	endif
	set MultifileIO_FileReader_canRead_result = FileReader_canRead(this_2)
	return MultifileIO_FileReader_canRead_result
endfunction

function check_executeWhile_BufferAdapters_576 takes integer this_2 returns boolean
	return dispatch_FileReader_MultifileIO_FileReader_canRead(reader[this_2])
endfunction

function check_executeWhile_BufferAdapters_577 takes integer this_2 returns boolean
	return dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_hasChunk(buffer[this_2])
endfunction

function check_executeWhile_GamecacheBuffer_GamecacheBuffer takes integer this_2 returns boolean
	return GamecacheBuffer_syncCounter[this_811[this_2]] <= GamecacheBuffer_writeIndex[this_811[this_2]]
endfunction

function check_executeWhile_Network_Network takes integer this_2 returns boolean
	return dispatch_HashBuffer_HashBuffer_HashBuffer_hasString(Network_dataBuffer[this_813[this_2]])
endfunction

function GamecacheBuffer_getRemainingWrite takes integer this_2 returns integer
	return GamecacheKeys_count - GamecacheBuffer_writeIndex[this_2] - 1
endfunction

function dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_getRemainingWrite takes integer this_2 returns integer
	local integer GamecacheBuffer_GamecacheBuffer_getRemainingWrite_result
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.getRemainingWrite")
		else
			call error("Called GamecacheBuffer.getRemainingWrite on invalid object.")
		endif
	endif
	set GamecacheBuffer_GamecacheBuffer_getRemainingWrite_result = GamecacheBuffer_getRemainingWrite(this_2)
	return GamecacheBuffer_GamecacheBuffer_getRemainingWrite_result
endfunction

function check_executeWhile_Network_Network_580 takes integer this_2 returns boolean
	return Network_counters_ints[this_815[this_2]] < Network_meta_ints[this_815[this_2]] and dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_getRemainingWrite(Network_gcIntBuffer[this_815[this_2]]) > 0
endfunction

function GamecacheBuffer_getRemainingRead takes integer this_2 returns integer
	return GamecacheKeys_count - GamecacheBuffer_readIndex[this_2] - 1
endfunction

function dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_getRemainingRead takes integer this_2 returns integer
	local integer GamecacheBuffer_GamecacheBuffer_getRemainingRead_result
	if GamecacheBuffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling GamecacheBuffer.getRemainingRead")
		else
			call error("Called GamecacheBuffer.getRemainingRead on invalid object.")
		endif
	endif
	set GamecacheBuffer_GamecacheBuffer_getRemainingRead_result = GamecacheBuffer_getRemainingRead(this_2)
	return GamecacheBuffer_GamecacheBuffer_getRemainingRead_result
endfunction

function check_executeWhile_Network_Network_581 takes integer this_2 returns boolean
	return Network_counters_ints[this_817[this_2]] < Network_meta_ints[this_817[this_2]] and dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_getRemainingRead(Network_gcIntBuffer[this_817[this_2]]) > 0
endfunction

function check_executeWhile_Network_Network_582 takes integer this_2 returns boolean
	return Network_counters_reals[this_819[this_2]] < Network_meta_reals[this_819[this_2]] and dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_getRemainingWrite(Network_gcRealBuffer[this_819[this_2]]) > 0
endfunction

function check_executeWhile_Network_Network_583 takes integer this_2 returns boolean
	return Network_counters_reals[this_821[this_2]] < Network_meta_reals[this_821[this_2]] and dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_getRemainingRead(Network_gcRealBuffer[this_821[this_2]]) > 0
endfunction

function check_executeWhile_Network_Network_584 takes integer this_2 returns boolean
	return Network_counters_booleans[this_823[this_2]] < Network_meta_booleans[this_823[this_2]] and dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_getRemainingWrite(Network_gcBooleanBuffer[this_823[this_2]]) > 0
endfunction

function check_executeWhile_Network_Network_585 takes integer this_2 returns boolean
	return Network_counters_booleans[this_825[this_2]] < Network_meta_booleans[this_825[this_2]] and dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_getRemainingRead(Network_gcBooleanBuffer[this_825[this_2]]) > 0
endfunction

function check_executeWhile_Network_Network_586 takes integer this_2 returns boolean
	return Network_counters_asciiInts[this_827[this_2]] < Network_meta_asciiInts[this_827[this_2]] and dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_getRemainingWrite(Network_gcStringBuffer[this_827[this_2]]) > 0
endfunction

function check_executeWhile_Network_Network_587 takes integer this_2 returns boolean
	return Network_counters_asciiInts[this_829[this_2]] < Network_meta_asciiInts[this_829[this_2]] and dispatch_GamecacheBuffer_GamecacheBuffer_GamecacheBuffer_getRemainingRead(Network_gcStringBuffer[this_829[this_2]]) > 0
endfunction

function StringEncoder_getStringCount takes integer this_2 returns integer
	return StringEncoder_stringCount[this_2]
endfunction

function dispatch_StringEncoder_StringEncoder_StringEncoder_getStringCount takes integer this_2 returns integer
	local integer StringEncoder_StringEncoder_getStringCount_result
	if StringEncoder_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling StringEncoder.getStringCount")
		else
			call error("Called StringEncoder.getStringCount on invalid object.")
		endif
	endif
	set StringEncoder_StringEncoder_getStringCount_result = StringEncoder_getStringCount(this_2)
	return StringEncoder_StringEncoder_getStringCount_result
endfunction

function check_executeWhile_Network_Network_588 takes integer this_2 returns boolean
	return dispatch_StringEncoder_StringEncoder_StringEncoder_getStringCount(Network_stringEncoder[this_833[this_2]]) > 0
endfunction

function check_executeWhile_OrderedStringBuffer_OrderedStringBuffer takes integer this_2 returns boolean
	return dispatch_AbstractStringBuffer_StringBuffer_AbstractStringBuffer_canRead(this_793[this_2])
endfunction

function check_executeWhile_StringEncoder_StringEncoder takes integer this_2 returns boolean
	return StringEncoder_counter[this_835[this_2]] < length[this_2]
endfunction

function check_executeWhile_StringEncoder_StringEncoder_591 takes integer this_2 returns boolean
	return StringEncoder_counter[this_837[this_2]] < length_654[this_2]
endfunction

function check_executeWhile_StringEncoder_StringEncoder_592 takes integer this_2 returns boolean
	return StringEncoder_stringCount[this_839[this_2]] > 0
endfunction

function check_executeWhile_StringEncoder_StringEncoder_593 takes integer this_2 returns boolean
	return StringEncoder_asciiIntCount[this_841[this_2]] > 0
endfunction

function dispatch_LimitedExecuteCondition_Execute_LimitedExecuteCondition_check takes integer this_2 returns boolean
	local boolean Execute_LimitedExecuteCondition_check_result
	if LimitedExecuteCondition_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling LimitedExecuteCondition.check")
		else
			call error("Called LimitedExecuteCondition.check on invalid object.")
		endif
	endif
	if LimitedExecuteCondition_typeId[this_2] <= 780 then
		if LimitedExecuteCondition_typeId[this_2] <= 775 then
			if LimitedExecuteCondition_typeId[this_2] <= 773 then
				if LimitedExecuteCondition_typeId[this_2] <= 772 then
					if LimitedExecuteCondition_typeId[this_2] <= 771 then
						set Execute_LimitedExecuteCondition_check_result = check_executeWhile_BufferAdapters(this_2)
					else
						set Execute_LimitedExecuteCondition_check_result = check_executeWhile_BufferAdapters_576(this_2)
					endif
				else
					set Execute_LimitedExecuteCondition_check_result = check_executeWhile_BufferAdapters_577(this_2)
				endif
			elseif LimitedExecuteCondition_typeId[this_2] <= 774 then
				set Execute_LimitedExecuteCondition_check_result = check_executeWhile_GamecacheBuffer_GamecacheBuffer(this_2)
			else
				set Execute_LimitedExecuteCondition_check_result = check_executeWhile_Network_Network(this_2)
			endif
		elseif LimitedExecuteCondition_typeId[this_2] <= 778 then
			if LimitedExecuteCondition_typeId[this_2] <= 777 then
				if LimitedExecuteCondition_typeId[this_2] <= 776 then
					set Execute_LimitedExecuteCondition_check_result = check_executeWhile_Network_Network_580(this_2)
				else
					set Execute_LimitedExecuteCondition_check_result = check_executeWhile_Network_Network_581(this_2)
				endif
			else
				set Execute_LimitedExecuteCondition_check_result = check_executeWhile_Network_Network_582(this_2)
			endif
		elseif LimitedExecuteCondition_typeId[this_2] <= 779 then
			set Execute_LimitedExecuteCondition_check_result = check_executeWhile_Network_Network_583(this_2)
		else
			set Execute_LimitedExecuteCondition_check_result = check_executeWhile_Network_Network_584(this_2)
		endif
	elseif LimitedExecuteCondition_typeId[this_2] <= 785 then
		if LimitedExecuteCondition_typeId[this_2] <= 783 then
			if LimitedExecuteCondition_typeId[this_2] <= 782 then
				if LimitedExecuteCondition_typeId[this_2] <= 781 then
					set Execute_LimitedExecuteCondition_check_result = check_executeWhile_Network_Network_585(this_2)
				else
					set Execute_LimitedExecuteCondition_check_result = check_executeWhile_Network_Network_586(this_2)
				endif
			else
				set Execute_LimitedExecuteCondition_check_result = check_executeWhile_Network_Network_587(this_2)
			endif
		elseif LimitedExecuteCondition_typeId[this_2] <= 784 then
			set Execute_LimitedExecuteCondition_check_result = check_executeWhile_Network_Network_588(this_2)
		else
			set Execute_LimitedExecuteCondition_check_result = check_executeWhile_OrderedStringBuffer_OrderedStringBuffer(this_2)
		endif
	elseif LimitedExecuteCondition_typeId[this_2] <= 787 then
		if LimitedExecuteCondition_typeId[this_2] <= 786 then
			set Execute_LimitedExecuteCondition_check_result = check_executeWhile_StringEncoder_StringEncoder(this_2)
		else
			set Execute_LimitedExecuteCondition_check_result = check_executeWhile_StringEncoder_StringEncoder_591(this_2)
		endif
	elseif LimitedExecuteCondition_typeId[this_2] <= 788 then
		set Execute_LimitedExecuteCondition_check_result = check_executeWhile_StringEncoder_StringEncoder_592(this_2)
	else
		set Execute_LimitedExecuteCondition_check_result = check_executeWhile_StringEncoder_StringEncoder_593(this_2)
	endif
	return Execute_LimitedExecuteCondition_check_result
endfunction

function run_execute_Execute takes integer this_2 returns nothing
	local integer i_2 = 0
	loop
		exitwhen  not (dispatch_LimitedExecuteCondition_Execute_LimitedExecuteCondition_check(condition[this_2]) and i_2 < resetCount[this_2])
		call dispatch_LimitedExecuteAction_Execute_LimitedExecuteAction_run(action[this_2])
		set i_2 = i_2 + 1
	endloop
	if dispatch_LimitedExecuteCondition_Execute_LimitedExecuteCondition_check(condition[this_2]) then
		call executeWhileInternal(resetCount[this_2], condition[this_2], action[this_2])
	endif
endfunction

function run_execute_ForceTests takes integer this_2 returns nothing
	call int_assertEquals(ForceTests_testInt, 0)
	set ForceTests_testInt = 10
endfunction

function alloc_ForForceCallback_execute_GamecacheKeys_GamecacheKeys takes nothing returns integer
	local integer this_2
	if ForForceCallback_firstFree == 0 then
		if ForForceCallback_maxIndex < 32768 then
			set ForForceCallback_maxIndex = ForForceCallback_maxIndex + 1
			set this_2 = ForForceCallback_maxIndex
			set ForForceCallback_typeId[this_2] = 698
		else
			call error("Out of memory: Could not create ForForceCallback_execute_GamecacheKeys_GamecacheKeys.")
			set this_2 = 0
		endif
	else
		set ForForceCallback_firstFree = ForForceCallback_firstFree - 1
		set this_2 = ForForceCallback_nextFree[ForForceCallback_firstFree]
		set ForForceCallback_typeId[this_2] = 698
	endif
	return this_2
endfunction

function GamecacheKeys_precompute takes string prefix_2, integer depth_2 returns nothing
	local integer i_2
	local integer temp
	local integer i_3
	local integer temp_2
	local integer clVar
	if depth_2 == 0 then
		set i_2 = 0
		set temp = GamecacheKeys_GC_KEYS_LENGTH - 1
		loop
			exitwhen i_2 > temp
			set GamecacheKeys_keys[GamecacheKeys_count] = prefix_2 + string_charAt(GamecacheKeys_GC_KEYS, i_2)
			set GamecacheKeys_count = GamecacheKeys_count + 1
			set i_2 = i_2 + 1
		endloop
	else
		set i_3 = 0
		set temp_2 = GamecacheKeys_GC_KEYS_LENGTH - 1
		loop
			exitwhen i_3 > temp_2
			set clVar = alloc_ForForceCallback_execute_GamecacheKeys_GamecacheKeys()
			set prefix[clVar] = prefix_2
			set i[clVar] = i_3
			set depth[clVar] = depth_2
			call execute(clVar)
			set i_3 = i_3 + 1
		endloop
	endif
endfunction

function GamecacheKeys_precompute_107 takes integer length_2 returns nothing
	call GamecacheKeys_precompute("", length_2 - 1)
endfunction

function run_execute_GamecacheKeys takes integer this_2 returns nothing
	call GamecacheKeys_precompute_107(i_649[this_2])
endfunction

function run_execute_GamecacheKeys_GamecacheKeys takes integer this_2 returns nothing
	call GamecacheKeys_precompute(prefix[this_2] + string_charAt(GamecacheKeys_GC_KEYS, i[this_2]), depth[this_2] - 1)
endfunction

function booleanToIndex takes boolean u returns integer
	local integer cond_result
	if u then
		set cond_result = 1
	else
		set cond_result = 0
	endif
	return cond_result
endfunction

function groupToIndex takes group object returns integer
	return handle_getHandleId(object)
endfunction

function push takes group g returns nothing
	set GroupUtils_stack[GroupUtils_numStack] = g
	call dispatch_HashMap_HashMap_HashMap_put(GroupUtils_used, groupToIndex(GroupUtils_stack[GroupUtils_numStack]), booleanToIndex(false))
	set GroupUtils_numStack = GroupUtils_numStack + 1
endfunction

function run_execute_GroupUtils takes integer this_2 returns nothing
	local integer i_2
	local integer temp
	set GroupUtils_numTotal = GroupUtils_numTotal + createNow[this_2]
	set i_2 = 1
	set temp = createNow[this_2]
	loop
		exitwhen i_2 > temp
		call push(CreateGroup())
		set i_2 = i_2 + 1
	endloop
endfunction

function run_execute_Network_Network takes integer this_2 returns nothing
	call dispatch_Network_Network_Network_sendRound(this_832[this_2])
endfunction

function SerializableElement_serialize takes integer this_2, integer buffer_2 returns nothing
	call dispatch_Buffer_BufferInterface_Buffer_writeInt(buffer_2, SerializableElement_id[this_2])
	call dispatch_Buffer_BufferInterface_Buffer_writeInt(buffer_2, SerializableElement_value[this_2])
endfunction

function cyc_TestSerializable_serialize takes integer funcChoice, integer this_2, integer buffer_2 returns nothing
	local integer iterator
	local integer element
	if funcChoice == 0 then
		call dispatch_Buffer_BufferInterface_Buffer_writeString(buffer_2, TestSerializable_name[this_2])
		call dispatch_Buffer_BufferInterface_Buffer_writeInt(buffer_2, TestSerializable_id[this_2])
		call dispatch_Buffer_BufferInterface_Buffer_writeReal(buffer_2, TestSerializable_facing[this_2])
		call dispatch_Buffer_BufferInterface_Buffer_writeInt(buffer_2, dispatch_LinkedList_LinkedList_LinkedList_size(TestSerializable_elements[this_2]))
		set iterator = LinkedList_iterator(TestSerializable_elements[this_2])
		loop
			exitwhen  not LLIterator_hasNext(iterator)
			set element = LLIterator_next(iterator)
			call cyc_TestSerializable_serialize(2, buffer_2, element)
		endloop
		call LLIterator_close(iterator)
	elseif funcChoice == 1 then
		call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkWrite(this_2)
		call cyc_TestSerializable_serialize(3, buffer_2, this_2)
	elseif funcChoice == 2 then
		if Buffer_typeId[this_2] == 0 then
			if this_2 == 0 then
				call error("Nullpointer exception when calling Buffer.write")
			else
				call error("Called Buffer.write on invalid object.")
			endif
		endif
		call cyc_TestSerializable_serialize(1, this_2, buffer_2)
	elseif funcChoice == 3 then
		if BufferSerializable_typeId[this_2] == 0 then
			if this_2 == 0 then
				call error("Nullpointer exception when calling BufferSerializable.serialize")
			else
				call error("Called BufferSerializable.serialize on invalid object.")
			endif
		endif
		if BufferSerializable_typeId[this_2] <= 648 then
			call SerializableElement_serialize(this_2, buffer_2)
		else
			call cyc_TestSerializable_serialize(0, this_2, buffer_2)
		endif
	endif
endfunction

function dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_write takes integer this_2, integer object returns nothing
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling AbstractBuffer.write")
		else
			call error("Called AbstractBuffer.write on invalid object.")
		endif
	endif
	call cyc_TestSerializable_serialize(1, this_2, object)
endfunction

function run_execute_PersistentStore_Persistable takes integer this_2 returns nothing
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_write(buffer_616[this_2], PersistentStore_entity[this_799[this_2]])
endfunction

function precomputeLookup takes nothing returns nothing
	local integer i_2 = 0
	local integer temp = StringEncoder_STRING_ALPHABET_LENGTH - 1
	local string c
	loop
		exitwhen i_2 > temp
		set c = string_charAt(StringEncoder_STRING_ALPHABET, i_2)
		if (string_toUpperCase(c) == c and string_toLowerCase(c) != c) or c == "/" then
			call dispatch_HashMap_HashMap_HashMap_put(StringEncoder_alphabetLookup, string_getHash("00" + c), i_2)
		else
			call dispatch_HashMap_HashMap_HashMap_put(StringEncoder_alphabetLookup, string_getHash(c), i_2)
		endif
		set i_2 = i_2 + 1
	endloop
endfunction

function run_execute_StringEncoder takes integer this_2 returns nothing
	call precomputeLookup()
endfunction

function testCollisions takes nothing returns nothing
	local integer i_2 = 0
	local integer temp = StringEncoder_STRING_ALPHABET_LENGTH - 1
	loop
		exitwhen i_2 > temp
		if getIndexFromChar(getCharFromIndex(i_2)) != i_2 then
			call Log_error("CollisionTest failed for " + int_toString(i_2) + " " + getCharFromIndex(i_2))
			call Log_error("getIndexFromChar() == " + int_toString(getIndexFromChar(getCharFromIndex(i_2))))
			call Log_error("getCharFromIndex(getIndexFromChar)) == " + getCharFromIndex(getIndexFromChar(getCharFromIndex(i_2))))
			call Log_error("This is a BAD thing and you should report it to wurst devs!")
		endif
		set i_2 = i_2 + 1
	endloop
endfunction

function run_execute_StringEncoder_1284 takes integer this_2 returns nothing
	call testCollisions()
endfunction

function alloc_LimitedExecuteAction_executeWhile_BufferAdapters_508 takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 752
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_BufferAdapters.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 752
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_BufferAdapters_525 takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 772
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_BufferAdapters.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 772
	endif
	return this_2
endfunction

function OrderedStringBuffer_populateFromFile takes integer this_2, integer reader_2 returns nothing
	local integer clVar = alloc_LimitedExecuteCondition_executeWhile_BufferAdapters_525()
	local integer clVar_2
	local integer temp
	set reader[clVar] = reader_2
	set temp = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_BufferAdapters_508()
	set this_791[clVar_2] = this_2
	set reader_660[clVar_2] = reader_2
	call executeWhile(64, temp, clVar_2)
endfunction

function run_try_load_PersistentStore_Persistable takes integer this_2 returns nothing
	call OrderedStringBuffer_populateFromFile(buffer_613[this_2], reader_662[this_2])
endfunction

function alloc_LimitedExecuteAction_executeWhile_BufferAdapters takes nothing returns integer
	local integer this_2
	if LimitedExecuteAction_firstFree == 0 then
		if LimitedExecuteAction_maxIndex < 32768 then
			set LimitedExecuteAction_maxIndex = LimitedExecuteAction_maxIndex + 1
			set this_2 = LimitedExecuteAction_maxIndex
			set LimitedExecuteAction_typeId[this_2] = 751
		else
			call error("Out of memory: Could not create LimitedExecuteAction_executeWhile_BufferAdapters.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteAction_firstFree = LimitedExecuteAction_firstFree - 1
		set this_2 = LimitedExecuteAction_nextFree[LimitedExecuteAction_firstFree]
		set LimitedExecuteAction_typeId[this_2] = 751
	endif
	return this_2
endfunction

function alloc_LimitedExecuteCondition_executeWhile_BufferAdapters takes nothing returns integer
	local integer this_2
	if LimitedExecuteCondition_firstFree == 0 then
		if LimitedExecuteCondition_maxIndex < 32768 then
			set LimitedExecuteCondition_maxIndex = LimitedExecuteCondition_maxIndex + 1
			set this_2 = LimitedExecuteCondition_maxIndex
			set LimitedExecuteCondition_typeId[this_2] = 771
		else
			call error("Out of memory: Could not create LimitedExecuteCondition_executeWhile_BufferAdapters.")
			set this_2 = 0
		endif
	else
		set LimitedExecuteCondition_firstFree = LimitedExecuteCondition_firstFree - 1
		set this_2 = LimitedExecuteCondition_nextFree[LimitedExecuteCondition_firstFree]
		set LimitedExecuteCondition_typeId[this_2] = 771
	endif
	return this_2
endfunction

function OrderedStringBuffer_transfer takes integer this_2, integer sink_2 returns nothing
	local integer clVar = alloc_LimitedExecuteCondition_executeWhile_BufferAdapters()
	local integer clVar_2
	local integer temp
	set this[clVar] = this_2
	set temp = clVar
	set clVar_2 = alloc_LimitedExecuteAction_executeWhile_BufferAdapters()
	set this_790[clVar_2] = this_2
	set sink[clVar_2] = sink_2
	call executeWhile(64, temp, clVar_2)
endfunction

function run_try_load_PersistentStore_Persistable_1293 takes integer this_2 returns nothing
	call OrderedStringBuffer_transfer(buffer_614[this_2], dispatch_Network_Network_Network_getData(network_656[this_2]))
endfunction

function HashBuffer_readInt takes integer this_2 returns integer
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkRead(this_2)
	set HashBuffer_integerReadIndex[this_2] = HashBuffer_integerReadIndex[this_2] + 1
	if  not dispatch_Table_Table_Table_hasInt(HashBuffer_buffer[this_2], HashBuffer_integerReadIndex[this_2]) then
		call error("HashBuffer: trying to read non-present int at pos#" + int_toString(HashBuffer_integerReadIndex[this_2]))
	endif
	return dispatch_Table_Table_Table_loadInt(HashBuffer_buffer[this_2], HashBuffer_integerReadIndex[this_2])
endfunction

function dispatch_Buffer_BufferInterface_Buffer_readInt takes integer this_2 returns integer
	local integer BufferInterface_Buffer_readInt_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Buffer.readInt")
		else
			call error("Called Buffer.readInt on invalid object.")
		endif
	endif
	if Buffer_typeId[this_2] <= 644 then
		set BufferInterface_Buffer_readInt_result = OrderedStringBuffer_readInt(this_2)
	else
		set BufferInterface_Buffer_readInt_result = HashBuffer_readInt(this_2)
	endif
	return BufferInterface_Buffer_readInt_result
endfunction

function SerializableElement_deserialize takes integer this_2, integer buffer_2 returns nothing
	set SerializableElement_id[this_2] = dispatch_Buffer_BufferInterface_Buffer_readInt(buffer_2)
	set SerializableElement_value[this_2] = dispatch_Buffer_BufferInterface_Buffer_readInt(buffer_2)
endfunction

function HashBuffer_readReal takes integer this_2 returns real
	call dispatch_AbstractBuffer_BufferInterface_AbstractBuffer_checkRead(this_2)
	set HashBuffer_realReadIndex[this_2] = HashBuffer_realReadIndex[this_2] + 1
	if  not dispatch_Table_Table_Table_hasReal(HashBuffer_buffer[this_2], HashBuffer_realReadIndex[this_2]) then
		call error("HashBuffer: trying to read non-present real at pos#" + int_toString(HashBuffer_realReadIndex[this_2]))
	endif
	return dispatch_Table_Table_Table_loadReal(HashBuffer_buffer[this_2], HashBuffer_realReadIndex[this_2])
endfunction

function dispatch_Buffer_BufferInterface_Buffer_readReal takes integer this_2 returns real
	local real BufferInterface_Buffer_readReal_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Buffer.readReal")
		else
			call error("Called Buffer.readReal on invalid object.")
		endif
	endif
	if Buffer_typeId[this_2] <= 644 then
		set BufferInterface_Buffer_readReal_result = OrderedStringBuffer_readReal(this_2)
	else
		set BufferInterface_Buffer_readReal_result = HashBuffer_readReal(this_2)
	endif
	return BufferInterface_Buffer_readReal_result
endfunction

function dispatch_Buffer_BufferInterface_Buffer_readString takes integer this_2 returns string
	local string BufferInterface_Buffer_readString_result
	if Buffer_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling Buffer.readString")
		else
			call error("Called Buffer.readString on invalid object.")
		endif
	endif
	if Buffer_typeId[this_2] <= 644 then
		set BufferInterface_Buffer_readString_result = OrderedStringBuffer_readString(this_2)
	else
		set BufferInterface_Buffer_readString_result = HashBuffer_readString(this_2)
	endif
	return BufferInterface_Buffer_readString_result
endfunction

function alloc_SerializableElement takes nothing returns integer
	local integer this_2
	if BufferSerializable_firstFree == 0 then
		if BufferSerializable_maxIndex < 32768 then
			set BufferSerializable_maxIndex = BufferSerializable_maxIndex + 1
			set this_2 = BufferSerializable_maxIndex
			set BufferSerializable_typeId[this_2] = 648
		else
			call error("Out of memory: Could not create SerializableElement.")
			set this_2 = 0
		endif
	else
		set BufferSerializable_firstFree = BufferSerializable_firstFree - 1
		set this_2 = BufferSerializable_nextFree[BufferSerializable_firstFree]
		set BufferSerializable_typeId[this_2] = 648
	endif
	return this_2
endfunction

function dispatch_SerializableElement_BufferTests_SerializableElement_deserialize takes integer this_2, integer buffer_2 returns nothing
	if BufferSerializable_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling SerializableElement.deserialize")
		else
			call error("Called SerializableElement.deserialize on invalid object.")
		endif
	endif
	call SerializableElement_deserialize(this_2, buffer_2)
endfunction

function construct_SerializableElement2 takes integer this_2, integer buffer_2 returns nothing
	call dispatch_SerializableElement_BufferTests_SerializableElement_deserialize(this_2, buffer_2)
endfunction

function new_SerializableElement takes integer buffer_2 returns integer
	local integer this_2 = alloc_SerializableElement()
	call construct_SerializableElement2(this_2, buffer_2)
	return this_2
endfunction

function TestSerializable_deserialize takes integer this_2, integer buffer_2 returns nothing
	local integer size
	local integer i_2
	local integer temp
	set TestSerializable_name[this_2] = dispatch_Buffer_BufferInterface_Buffer_readString(buffer_2)
	set TestSerializable_id[this_2] = dispatch_Buffer_BufferInterface_Buffer_readInt(buffer_2)
	set TestSerializable_facing[this_2] = dispatch_Buffer_BufferInterface_Buffer_readReal(buffer_2)
	set size = dispatch_Buffer_BufferInterface_Buffer_readInt(buffer_2)
	set i_2 = 0
	set temp = size - 1
	loop
		exitwhen i_2 > temp
		call dispatch_LinkedList_LinkedList_LinkedList_add_1(TestSerializable_elements[this_2], new_SerializableElement(buffer_2))
		set i_2 = i_2 + 1
	endloop
endfunction

function dispatch_BufferSerializable_BufferInterface_BufferSerializable_deserialize takes integer this_2, integer buffer_2 returns nothing
	if BufferSerializable_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling BufferSerializable.deserialize")
		else
			call error("Called BufferSerializable.deserialize on invalid object.")
		endif
	endif
	if BufferSerializable_typeId[this_2] <= 648 then
		call SerializableElement_deserialize(this_2, buffer_2)
	else
		call TestSerializable_deserialize(this_2, buffer_2)
	endif
endfunction

function run_try_start_onSynced_PersistentStore_Persistable takes integer this_2 returns nothing
	call dispatch_BufferSerializable_BufferInterface_BufferSerializable_deserialize(PersistentStore_entity[this_798[this_2]], buffer_615[this_2])
endfunction

function dispatch_ForForceCallback_Execute_ForForceCallback_run takes integer this_2 returns nothing
	if ForForceCallback_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling ForForceCallback.run")
		else
			call error("Called ForForceCallback.run on invalid object.")
		endif
	endif
	if ForForceCallback_typeId[this_2] <= 699 then
		if ForForceCallback_typeId[this_2] <= 696 then
			if ForForceCallback_typeId[this_2] <= 694 then
				if ForForceCallback_typeId[this_2] <= 693 then
					call run_execute_AbstractIOTaskExecutor_IOTaskExecutor(this_2)
				else
					call run_execute_AbstractIOTaskExecutor_IOTaskExecutor_1275(this_2)
				endif
			elseif ForForceCallback_typeId[this_2] <= 695 then
				call run_execute_Execute(this_2)
			else
				call run_execute_ForceTests(this_2)
			endif
		elseif ForForceCallback_typeId[this_2] <= 698 then
			if ForForceCallback_typeId[this_2] <= 697 then
				call run_execute_GamecacheKeys(this_2)
			else
				call run_execute_GamecacheKeys_GamecacheKeys(this_2)
			endif
		else
			call run_execute_GroupUtils(this_2)
		endif
	elseif ForForceCallback_typeId[this_2] <= 703 then
		if ForForceCallback_typeId[this_2] <= 701 then
			if ForForceCallback_typeId[this_2] <= 700 then
				call run_execute_Network_Network(this_2)
			else
				call run_execute_PersistentStore_Persistable(this_2)
			endif
		elseif ForForceCallback_typeId[this_2] <= 702 then
			call run_execute_StringEncoder(this_2)
		else
			call run_execute_StringEncoder_1284(this_2)
		endif
	elseif ForForceCallback_typeId[this_2] <= 705 then
		if ForForceCallback_typeId[this_2] <= 704 then
			call run_try_load_PersistentStore_Persistable(this_2)
		else
			call run_try_load_PersistentStore_Persistable_1293(this_2)
		endif
	else
		call run_try_start_onSynced_PersistentStore_Persistable(this_2)
	endif
endfunction

function getCurrentCallback takes nothing returns integer
	return Execute_tempCallbacks[Execute_tempCallbacksCount - 1]
endfunction

function setCurrentCallbackSuccess takes boolean value_2 returns nothing
	set Execute_tempCallbacksSuccess[Execute_tempCallbacksCount - 1] = value_2
endfunction

function executeCurrentCallback takes nothing returns nothing
	set ErrorHandling_lastError = ""
	call dispatch_ForForceCallback_Execute_ForForceCallback_run(getCurrentCallback())
	call setCurrentCallbackSuccess(true)
endfunction

function init_Abilities takes nothing returns boolean
	return true
endfunction

function init_AbilityIds takes nothing returns boolean
	return true
endfunction

function init_Angle takes nothing returns boolean
	set Angle_DEGTORAD = 0.017453293
	set Angle_RADTODEG = 57.295779513
	return true
endfunction

function init_Basics takes nothing returns boolean
	set Basics_ANIMATION_PERIOD = 0.030
	set Basics_HEIGHT_ENABLER = 1097691750
	set Basics_LOCUST_ID = 1097625443
	set Basics_GHOST_VIS_ID = 1097167976
	set Basics_DUMMY_PLAYER = Player_players[PLAYER_NEUTRAL_PASSIVE]
	return true
endfunction

function init_BigNum takes nothing returns boolean
	return true
endfunction

function initLbyte takes nothing returns nothing
	local integer i16 = 0
	local integer i256 = 0
	local integer i_2
	loop
		exitwhen  not (i256 < 256)
		set i_2 = 0
		loop
			exitwhen  not (i_2 < 16)
			set i256 = i256 + 1
			set i_2 = i_2 + 1
		endloop
		set i16 = i16 + 1
	endloop
endfunction

function initRbyte takes nothing returns nothing
	local integer i256 = 0
	local integer i16
	loop
		exitwhen  not (i256 < 256)
		set i16 = 0
		loop
			exitwhen  not (i16 < 16)
			set i256 = i256 + 1
			set i16 = i16 + 1
		endloop
	endloop
endfunction

function initShift takes nothing returns nothing
	local integer bit = 0
	local integer pow = 1
	loop
		exitwhen  not (bit <= 32)
		set pow = pow * 2
		set bit = bit + 1
	endloop
endfunction

function initBitwise takes nothing returns nothing
	call initLbyte()
	call initRbyte()
	call initShift()
endfunction

function init_BitwiseInit takes nothing returns boolean
	call initBitwise()
	return true
endfunction

function init_Buildings takes nothing returns boolean
	return true
endfunction

function alloc_OrderStringFactory takes nothing returns integer
	local integer this_2
	if OrderStringFactory_firstFree == 0 then
		if OrderStringFactory_maxIndex < 32768 then
			set OrderStringFactory_maxIndex = OrderStringFactory_maxIndex + 1
			set this_2 = OrderStringFactory_maxIndex
		else
			call error("Out of memory: Could not create OrderStringFactory.")
			set this_2 = 0
		endif
	else
		set OrderStringFactory_firstFree = OrderStringFactory_firstFree - 1
		set this_2 = OrderStringFactory_nextFree[OrderStringFactory_firstFree]
	endif
	return this_2
endfunction

function construct_OrderStringFactory takes integer this_2 returns nothing
endfunction

function new_OrderStringFactory takes nothing returns integer
	local integer this_2 = alloc_OrderStringFactory()
	call construct_OrderStringFactory(this_2)
	return this_2
endfunction

function init_ChannelAbilityPreset takes nothing returns boolean
	call new_OrderStringFactory()
	return true
endfunction

function alloc_CallbackSingle_nullTimer_ClosureEvents takes nothing returns integer
	local integer this_2
	if CallbackSingle_firstFree == 0 then
		if CallbackSingle_maxIndex < 32768 then
			set CallbackSingle_maxIndex = CallbackSingle_maxIndex + 1
			set this_2 = CallbackSingle_maxIndex
			set CallbackSingle_typeId[this_2] = 659
		else
			call error("Out of memory: Could not create CallbackSingle_nullTimer_ClosureEvents.")
			set this_2 = 0
		endif
	else
		set CallbackSingle_firstFree = CallbackSingle_firstFree - 1
		set this_2 = CallbackSingle_nextFree[CallbackSingle_firstFree]
		set CallbackSingle_typeId[this_2] = 659
	endif
	return this_2
endfunction

function construct_CallbackSingle takes integer this_2 returns nothing
endfunction

function alloc_HashMap takes nothing returns integer
	local integer this_2
	if Table_firstFree == 0 then
		if Table_maxIndex < 32768 then
			set Table_maxIndex = Table_maxIndex + 1
			set this_2 = Table_maxIndex
			set Table_typeId[this_2] = 856
		else
			call error("Out of memory: Could not create HashMap.")
			set this_2 = 0
		endif
	else
		set Table_firstFree = Table_firstFree - 1
		set this_2 = Table_nextFree[Table_firstFree]
		set Table_typeId[this_2] = 856
	endif
	return this_2
endfunction

function construct_Table takes integer this_2 returns nothing
endfunction

function construct_HashMap takes integer this_2 returns nothing
	call construct_Table(this_2)
	set HashMap_size[this_2] = 0
endfunction

function new_HashMap takes nothing returns integer
	local integer this_2 = alloc_HashMap()
	call construct_HashMap(this_2)
	return this_2
endfunction

function getTimer takes nothing returns timer
	local timer receiver
	if TimerUtils_freeTimersCount > 0 then
		set TimerUtils_freeTimersCount = TimerUtils_freeTimersCount - 1
		call timer_setData(TimerUtils_freeTimers[TimerUtils_freeTimersCount], 0)
		set receiver = null
		return TimerUtils_freeTimers[TimerUtils_freeTimersCount]
	else
		set receiver = CreateTimer()
		call timer_setData(receiver, 0)
		set getTimertempReturn = receiver
		set receiver = null
		return getTimertempReturn
	endif
endfunction

function CallbackSingle_start takes integer this_2, timer whichTimer, real time returns nothing
	local timer receiver = whichTimer
	local timer receiver_2
	call timer_setData(receiver, this_2)
	set receiver_2 = receiver
	call timer_start(receiver_2, time, ref_function_code__start_CallbackSingle_ClosureTimers)
	set CallbackSingle_t[this_2] = receiver_2
	set receiver = null
	set receiver_2 = null
endfunction

function dispatch_CallbackSingle_ClosureTimers_CallbackSingle_start takes integer this_2, timer whichTimer, real time returns nothing
	if CallbackSingle_typeId[this_2] == 0 then
		if this_2 == 0 then
			call error("Nullpointer exception when calling CallbackSingle.start")
		else
			call error("Called CallbackSingle.start on invalid object.")
		endif
	endif
	call CallbackSingle_start(this_2, whichTimer, time)
endfunction

function timer_doAfter takes timer this_2, real timeToWait, integer cb returns integer
	call dispatch_CallbackSingle_ClosureTimers_CallbackSingle_start(cb, this_2, timeToWait)
	return cb
endfunction

function doAfter takes real timeToWait, integer cb returns integer
	return timer_doAfter(getTimer(), timeToWait, cb)
endfunction

function nullTimer takes integer cb returns integer
	return doAfter(0., cb)
endfunction

function onUnitDeindex takes code func returns nothing
	call trigger_addCondition(UnitIndexer_onDeindexTrigger, Condition(func))
endfunction

function onUnitIndex takes code func returns nothing
	call trigger_addCondition(UnitIndexer_onIndexTrigger, Condition(func))
endfunction

function init_ClosureEvents takes nothing returns boolean
	local integer clVar
	set ClosureEvents_EVENT_PLAYER_CHAT_FILTER = ConvertPlayerEvent(96)
	set EventListener_castMap = new_HashMap()
	set EventListener_castMapCasters = new_HashMap()
	set EventListener_useMouseEvents = false
	set ClosureEvents_unitTrig = CreateTrigger()
	set ClosureEvents_leaveTrig = CreateTrigger()
	set ClosureEvents_keyTrig = CreateTrigger()
	set ClosureEvents_eventTypeCounter = 0
	call onUnitIndex(ref_function_code__onUnitIndex_ClosureEvents)
	call onUnitDeindex(ref_function_code__onUnitDeindex_ClosureEvents)
	set clVar = alloc_CallbackSingle_nullTimer_ClosureEvents()
	call construct_CallbackSingle(clVar)
	call nullTimer(clVar)
	return true
endfunction

function init_ClosureForGroups takes nothing returns boolean
	local real tuple_temp
	local real tuple_temp_2
	set ClosureForGroups_DUMMY_GROUP = CreateGroup()
	set ClosureForGroups_tempCallbacksCount = 0
	set ClosureForGroups_maxCount = Integer_INT_MAX
	set ClosureForGroups_iterCount = 0
	set ClosureForGroups_filter = Filter(ref_function_code__Filter_ClosureForGroups)
	set ClosureForGroups_tempCallbacksDCount = 0
	set ClosureForGroups_nearestDDist = Real_REAL_MAX
	set tuple_temp = Vectors_ZERO2_x
	set tuple_temp_2 = Vectors_ZERO2_y
	set ClosureForGroups_gpos_x = tuple_temp
	set ClosureForGroups_gpos_y = tuple_temp_2
	return true
endfunction

function init_ClosureTimers takes nothing returns boolean
	set ClosureTimers_x = 200
	return true
endfunction

function initializeTable takes nothing returns nothing
	local integer i_2 = 0
	loop
		exitwhen i_2 > 15
		call dispatch_Table_Table_Table_saveInt(Colors_decs, string_getHash(Colors_hexs[i_2]), i_2)
		set i_2 = i_2 + 1
	endloop
endfunction

function alloc_Table takes nothing returns integer
	local integer this_2
	if Table_firstFree == 0 then
		if Table_maxIndex < 32768 then
			set Table_maxIndex = Table_maxIndex + 1
			set this_2 = Table_maxIndex
			set Table_typeId[this_2] = 855
		else
			call error("Out of memory: Could not create Table.")
			set this_2 = 0
		endif
	else
		set Table_firstFree = Table_firstFree - 1
		set this_2 = Table_nextFree[Table_firstFree]
		set Table_typeId[this_2] = 855
	endif
	return this_2
endfunction

function new_Table takes nothing returns integer
	local integer this_2 = alloc_Table()
	call construct_Table(this_2)
	return this_2
endfunction

function init_Colors takes nothing returns boolean
	local integer tuple_temp = 255
	local integer tuple_temp_2 = 255
	local integer tuple_temp_3 = 255
	local integer tuple_temp_4 = 255
	local integer tuple_temp_5
	local integer tuple_temp_6
	local integer tuple_temp_7
	local integer tuple_temp_8
	local integer tuple_temp_9
	local integer tuple_temp_10
	local integer tuple_temp_11
	local integer tuple_temp_12
	local integer tuple_temp_13
	local integer tuple_temp_14
	local integer tuple_temp_15
	local integer tuple_temp_16
	local integer tuple_temp_17
	local integer tuple_temp_18
	local integer tuple_temp_19
	local integer tuple_temp_20
	local integer tuple_temp_21
	local integer tuple_temp_22
	local integer tuple_temp_23
	local integer tuple_temp_24
	local integer tuple_temp_25
	local integer tuple_temp_26
	local integer tuple_temp_27
	local integer tuple_temp_28
	local integer tuple_temp_29
	local integer tuple_temp_30
	local integer tuple_temp_31
	local integer tuple_temp_32
	local integer tuple_temp_33
	local integer tuple_temp_34
	local integer tuple_temp_35
	local integer tuple_temp_36
	local integer tuple_temp_37
	local integer tuple_temp_38
	local integer tuple_temp_39
	local integer tuple_temp_40
	local integer tuple_temp_41
	local integer tuple_temp_42
	local integer tuple_temp_43
	local integer tuple_temp_44
	local integer tuple_temp_45
	local integer tuple_temp_46
	local integer tuple_temp_47
	local integer tuple_temp_48
	local integer tuple_temp_49
	local integer tuple_temp_50
	local integer tuple_temp_51
	local integer tuple_temp_52
	local integer tuple_temp_53
	local integer tuple_temp_54
	local integer tuple_temp_55
	local integer tuple_temp_56
	local integer tuple_temp_57
	local integer tuple_temp_58
	local integer tuple_temp_59
	local integer tuple_temp_60
	local integer tuple_temp_61
	local integer tuple_temp_62
	local integer tuple_temp_63
	local integer tuple_temp_64
	local integer tuple_temp_65
	local integer tuple_temp_66
	local integer tuple_temp_67
	local integer tuple_temp_68
	local integer tuple_temp_69
	local integer tuple_temp_70
	local integer tuple_temp_71
	local integer tuple_temp_72
	local integer tuple_temp_73
	local integer tuple_temp_74
	local integer tuple_temp_75
	local integer tuple_temp_76
	local integer tuple_temp_77
	local integer tuple_temp_78
	local integer tuple_temp_79
	local integer tuple_temp_80
	local integer tuple_temp_81
	local integer tuple_temp_82
	local integer tuple_temp_83
	local integer tuple_temp_84
	local integer tuple_temp_85
	local integer tuple_temp_86
	local integer tuple_temp_87
	local integer tuple_temp_88
	local integer tuple_temp_89
	local integer tuple_temp_90
	local integer tuple_temp_91
	local integer tuple_temp_92
	local integer tuple_temp_93
	local integer tuple_temp_94
	local integer tuple_temp_95
	local integer tuple_temp_96
	local integer tuple_temp_97
	local integer tuple_temp_98
	local integer tuple_temp_99
	local integer tuple_temp_100
	local integer tuple_temp_101
	local integer tuple_temp_102
	local integer tuple_temp_103
	local integer tuple_temp_104
	local integer tuple_temp_105
	local integer tuple_temp_106
	local integer tuple_temp_107
	local integer tuple_temp_108
	local integer tuple_temp_109
	local integer tuple_temp_110
	local integer tuple_temp_111
	local integer tuple_temp_112
	local integer tuple_temp_113
	local integer tuple_temp_114
	local integer tuple_temp_115
	local integer tuple_temp_116
	local integer tuple_temp_117
	local integer tuple_temp_118
	local integer tuple_temp_119
	local integer tuple_temp_120
	local integer tuple_temp_121
	local integer tuple_temp_122
	local integer tuple_temp_123
	local integer tuple_temp_124
	local integer tuple_temp_125
	local integer tuple_temp_126
	local integer tuple_temp_127
	local integer tuple_temp_128
	local integer tuple_temp_129
	local integer tuple_temp_130
	local integer tuple_temp_131
	local integer tuple_temp_132
	local integer tuple_temp_133
	local integer tuple_temp_134
	local integer tuple_temp_135
	local integer tuple_temp_136
	local integer tuple_temp_137
	local integer tuple_temp_138
	local integer tuple_temp_139
	local integer tuple_temp_140
	local integer tuple_temp_141
	local integer tuple_temp_142
	local integer tuple_temp_143
	local integer tuple_temp_144
	local integer tuple_temp_145
	local integer tuple_temp_146
	local integer tuple_temp_147
	local integer tuple_temp_148
	local integer tuple_temp_149
	local integer tuple_temp_150
	local integer tuple_temp_151
	local integer tuple_temp_152
	local integer tuple_temp_153
	local integer tuple_temp_154
	local integer tuple_temp_155
	local integer tuple_temp_156
	set Colors_COLOR_WHITE_red = tuple_temp
	set Colors_COLOR_WHITE_green = tuple_temp_2
	set Colors_COLOR_WHITE_blue = tuple_temp_3
	set Colors_COLOR_WHITE_alpha = tuple_temp_4
	set tuple_temp_5 = 0
	set tuple_temp_6 = 0
	set tuple_temp_7 = 0
	set tuple_temp_8 = 0
	set tuple_temp_9 = 255
	set tuple_temp_10 = 204
	set tuple_temp_11 = 0
	set tuple_temp_12 = 255
	set tuple_temp_13 = 255
	set tuple_temp_14 = 2
	set tuple_temp_15 = 2
	set Colors_PLAYER_COLORS_red[0] = tuple_temp_13
	set Colors_PLAYER_COLORS_green[0] = tuple_temp_14
	set Colors_PLAYER_COLORS_blue[0] = tuple_temp_15
	set tuple_temp_16 = 0
	set tuple_temp_17 = 65
	set tuple_temp_18 = 255
	set Colors_PLAYER_COLORS_red[1] = tuple_temp_16
	set Colors_PLAYER_COLORS_green[1] = tuple_temp_17
	set Colors_PLAYER_COLORS_blue[1] = tuple_temp_18
	set tuple_temp_19 = 27
	set tuple_temp_20 = 229
	set tuple_temp_21 = 184
	set Colors_PLAYER_COLORS_red[2] = tuple_temp_19
	set Colors_PLAYER_COLORS_green[2] = tuple_temp_20
	set Colors_PLAYER_COLORS_blue[2] = tuple_temp_21
	set tuple_temp_22 = 83
	set tuple_temp_23 = 0
	set tuple_temp_24 = 128
	set Colors_PLAYER_COLORS_red[3] = tuple_temp_22
	set Colors_PLAYER_COLORS_green[3] = tuple_temp_23
	set Colors_PLAYER_COLORS_blue[3] = tuple_temp_24
	set tuple_temp_25 = 255
	set tuple_temp_26 = 252
	set tuple_temp_27 = 0
	set Colors_PLAYER_COLORS_red[4] = tuple_temp_25
	set Colors_PLAYER_COLORS_green[4] = tuple_temp_26
	set Colors_PLAYER_COLORS_blue[4] = tuple_temp_27
	set tuple_temp_28 = 254
	set tuple_temp_29 = 137
	set tuple_temp_30 = 13
	set Colors_PLAYER_COLORS_red[5] = tuple_temp_28
	set Colors_PLAYER_COLORS_green[5] = tuple_temp_29
	set Colors_PLAYER_COLORS_blue[5] = tuple_temp_30
	set tuple_temp_31 = 31
	set tuple_temp_32 = 191
	set tuple_temp_33 = 0
	set Colors_PLAYER_COLORS_red[6] = tuple_temp_31
	set Colors_PLAYER_COLORS_green[6] = tuple_temp_32
	set Colors_PLAYER_COLORS_blue[6] = tuple_temp_33
	set tuple_temp_34 = 228
	set tuple_temp_35 = 90
	set tuple_temp_36 = 175
	set Colors_PLAYER_COLORS_red[7] = tuple_temp_34
	set Colors_PLAYER_COLORS_green[7] = tuple_temp_35
	set Colors_PLAYER_COLORS_blue[7] = tuple_temp_36
	set tuple_temp_37 = 148
	set tuple_temp_38 = 149
	set tuple_temp_39 = 150
	set Colors_PLAYER_COLORS_red[8] = tuple_temp_37
	set Colors_PLAYER_COLORS_green[8] = tuple_temp_38
	set Colors_PLAYER_COLORS_blue[8] = tuple_temp_39
	set tuple_temp_40 = 125
	set tuple_temp_41 = 190
	set tuple_temp_42 = 241
	set Colors_PLAYER_COLORS_red[9] = tuple_temp_40
	set Colors_PLAYER_COLORS_green[9] = tuple_temp_41
	set Colors_PLAYER_COLORS_blue[9] = tuple_temp_42
	set tuple_temp_43 = 15
	set tuple_temp_44 = 97
	set tuple_temp_45 = 69
	set Colors_PLAYER_COLORS_red[10] = tuple_temp_43
	set Colors_PLAYER_COLORS_green[10] = tuple_temp_44
	set Colors_PLAYER_COLORS_blue[10] = tuple_temp_45
	set tuple_temp_46 = 77
	set tuple_temp_47 = 41
	set tuple_temp_48 = 3
	set Colors_PLAYER_COLORS_red[11] = tuple_temp_46
	set Colors_PLAYER_COLORS_green[11] = tuple_temp_47
	set Colors_PLAYER_COLORS_blue[11] = tuple_temp_48
	set tuple_temp_49 = 155
	set tuple_temp_50 = 0
	set tuple_temp_51 = 0
	set Colors_PLAYER_COLORS_red[12] = tuple_temp_49
	set Colors_PLAYER_COLORS_green[12] = tuple_temp_50
	set Colors_PLAYER_COLORS_blue[12] = tuple_temp_51
	set tuple_temp_52 = 0
	set tuple_temp_53 = 0
	set tuple_temp_54 = 195
	set Colors_PLAYER_COLORS_red[13] = tuple_temp_52
	set Colors_PLAYER_COLORS_green[13] = tuple_temp_53
	set Colors_PLAYER_COLORS_blue[13] = tuple_temp_54
	set tuple_temp_55 = 0
	set tuple_temp_56 = 234
	set tuple_temp_57 = 255
	set Colors_PLAYER_COLORS_red[14] = tuple_temp_55
	set Colors_PLAYER_COLORS_green[14] = tuple_temp_56
	set Colors_PLAYER_COLORS_blue[14] = tuple_temp_57
	set tuple_temp_58 = 190
	set tuple_temp_59 = 0
	set tuple_temp_60 = 254
	set Colors_PLAYER_COLORS_red[15] = tuple_temp_58
	set Colors_PLAYER_COLORS_green[15] = tuple_temp_59
	set Colors_PLAYER_COLORS_blue[15] = tuple_temp_60
	set tuple_temp_61 = 235
	set tuple_temp_62 = 205
	set tuple_temp_63 = 135
	set Colors_PLAYER_COLORS_red[16] = tuple_temp_61
	set Colors_PLAYER_COLORS_green[16] = tuple_temp_62
	set Colors_PLAYER_COLORS_blue[16] = tuple_temp_63
	set tuple_temp_64 = 248
	set tuple_temp_65 = 164
	set tuple_temp_66 = 139
	set Colors_PLAYER_COLORS_red[17] = tuple_temp_64
	set Colors_PLAYER_COLORS_green[17] = tuple_temp_65
	set Colors_PLAYER_COLORS_blue[17] = tuple_temp_66
	set tuple_temp_67 = 191
	set tuple_temp_68 = 255
	set tuple_temp_69 = 128
	set Colors_PLAYER_COLORS_red[18] = tuple_temp_67
	set Colors_PLAYER_COLORS_green[18] = tuple_temp_68
	set Colors_PLAYER_COLORS_blue[18] = tuple_temp_69
	set tuple_temp_70 = 220
	set tuple_temp_71 = 185
	set tuple_temp_72 = 235
	set Colors_PLAYER_COLORS_red[19] = tuple_temp_70
	set Colors_PLAYER_COLORS_green[19] = tuple_temp_71
	set Colors_PLAYER_COLORS_blue[19] = tuple_temp_72
	set tuple_temp_73 = 40
	set tuple_temp_74 = 40
	set tuple_temp_75 = 40
	set Colors_PLAYER_COLORS_red[20] = tuple_temp_73
	set Colors_PLAYER_COLORS_green[20] = tuple_temp_74
	set Colors_PLAYER_COLORS_blue[20] = tuple_temp_75
	set tuple_temp_76 = 235
	set tuple_temp_77 = 240
	set tuple_temp_78 = 255
	set Colors_PLAYER_COLORS_red[21] = tuple_temp_76
	set Colors_PLAYER_COLORS_green[21] = tuple_temp_77
	set Colors_PLAYER_COLORS_blue[21] = tuple_temp_78
	set tuple_temp_79 = 0
	set tuple_temp_80 = 120
	set tuple_temp_81 = 30
	set Colors_PLAYER_COLORS_red[22] = tuple_temp_79
	set Colors_PLAYER_COLORS_green[22] = tuple_temp_80
	set Colors_PLAYER_COLORS_blue[22] = tuple_temp_81
	set tuple_temp_82 = 164
	set tuple_temp_83 = 11
	set tuple_temp_84 = 51
	set Colors_PLAYER_COLORS_red[23] = tuple_temp_82
	set Colors_PLAYER_COLORS_green[23] = tuple_temp_83
	set Colors_PLAYER_COLORS_blue[23] = tuple_temp_84
	set tuple_temp_85 = Colors_PLAYER_COLORS_red[0]
	set tuple_temp_86 = Colors_PLAYER_COLORS_green[0]
	set tuple_temp_87 = Colors_PLAYER_COLORS_blue[0]
	set tuple_temp_88 = Colors_PLAYER_COLORS_red[1]
	set tuple_temp_89 = Colors_PLAYER_COLORS_green[1]
	set tuple_temp_90 = Colors_PLAYER_COLORS_blue[1]
	set tuple_temp_91 = Colors_PLAYER_COLORS_red[2]
	set tuple_temp_92 = Colors_PLAYER_COLORS_green[2]
	set tuple_temp_93 = Colors_PLAYER_COLORS_blue[2]
	set tuple_temp_94 = Colors_PLAYER_COLORS_red[3]
	set tuple_temp_95 = Colors_PLAYER_COLORS_green[3]
	set tuple_temp_96 = Colors_PLAYER_COLORS_blue[3]
	set tuple_temp_97 = Colors_PLAYER_COLORS_red[4]
	set tuple_temp_98 = Colors_PLAYER_COLORS_green[4]
	set tuple_temp_99 = Colors_PLAYER_COLORS_blue[4]
	set tuple_temp_100 = Colors_PLAYER_COLORS_red[5]
	set tuple_temp_101 = Colors_PLAYER_COLORS_green[5]
	set tuple_temp_102 = Colors_PLAYER_COLORS_blue[5]
	set tuple_temp_103 = Colors_PLAYER_COLORS_red[6]
	set tuple_temp_104 = Colors_PLAYER_COLORS_green[6]
	set tuple_temp_105 = Colors_PLAYER_COLORS_blue[6]
	set tuple_temp_106 = Colors_PLAYER_COLORS_red[7]
	set tuple_temp_107 = Colors_PLAYER_COLORS_green[7]
	set tuple_temp_108 = Colors_PLAYER_COLORS_blue[7]
	set tuple_temp_109 = Colors_PLAYER_COLORS_red[8]
	set tuple_temp_110 = Colors_PLAYER_COLORS_green[8]
	set tuple_temp_111 = Colors_PLAYER_COLORS_blue[8]
	set tuple_temp_112 = Colors_PLAYER_COLORS_red[9]
	set tuple_temp_113 = Colors_PLAYER_COLORS_green[9]
	set tuple_temp_114 = Colors_PLAYER_COLORS_blue[9]
	set tuple_temp_115 = Colors_PLAYER_COLORS_red[10]
	set tuple_temp_116 = Colors_PLAYER_COLORS_green[10]
	set tuple_temp_117 = Colors_PLAYER_COLORS_blue[10]
	set tuple_temp_118 = Colors_PLAYER_COLORS_red[11]
	set tuple_temp_119 = Colors_PLAYER_COLORS_green[11]
	set tuple_temp_120 = Colors_PLAYER_COLORS_blue[11]
	set tuple_temp_121 = Colors_PLAYER_COLORS_red[12]
	set tuple_temp_122 = Colors_PLAYER_COLORS_green[12]
	set tuple_temp_123 = Colors_PLAYER_COLORS_blue[12]
	set tuple_temp_124 = Colors_PLAYER_COLORS_red[13]
	set tuple_temp_125 = Colors_PLAYER_COLORS_green[13]
	set tuple_temp_126 = Colors_PLAYER_COLORS_blue[13]
	set tuple_temp_127 = Colors_PLAYER_COLORS_red[14]
	set tuple_temp_128 = Colors_PLAYER_COLORS_green[14]
	set tuple_temp_129 = Colors_PLAYER_COLORS_blue[14]
	set tuple_temp_130 = Colors_PLAYER_COLORS_red[15]
	set tuple_temp_131 = Colors_PLAYER_COLORS_green[15]
	set tuple_temp_132 = Colors_PLAYER_COLORS_blue[15]
	set tuple_temp_133 = Colors_PLAYER_COLORS_red[16]
	set tuple_temp_134 = Colors_PLAYER_COLORS_green[16]
	set tuple_temp_135 = Colors_PLAYER_COLORS_blue[16]
	set tuple_temp_136 = Colors_PLAYER_COLORS_red[17]
	set tuple_temp_137 = Colors_PLAYER_COLORS_green[17]
	set tuple_temp_138 = Colors_PLAYER_COLORS_blue[17]
	set tuple_temp_139 = Colors_PLAYER_COLORS_red[18]
	set tuple_temp_140 = Colors_PLAYER_COLORS_green[18]
	set tuple_temp_141 = Colors_PLAYER_COLORS_blue[18]
	set tuple_temp_142 = Colors_PLAYER_COLORS_red[19]
	set tuple_temp_143 = Colors_PLAYER_COLORS_green[19]
	set tuple_temp_144 = Colors_PLAYER_COLORS_blue[19]
	set tuple_temp_145 = Colors_PLAYER_COLORS_red[20]
	set tuple_temp_146 = Colors_PLAYER_COLORS_green[20]
	set tuple_temp_147 = Colors_PLAYER_COLORS_blue[20]
	set tuple_temp_148 = Colors_PLAYER_COLORS_red[21]
	set tuple_temp_149 = Colors_PLAYER_COLORS_green[21]
	set tuple_temp_150 = Colors_PLAYER_COLORS_blue[21]
	set tuple_temp_151 = Colors_PLAYER_COLORS_red[22]
	set tuple_temp_152 = Colors_PLAYER_COLORS_green[22]
	set tuple_temp_153 = Colors_PLAYER_COLORS_blue[22]
	set tuple_temp_154 = Colors_PLAYER_COLORS_red[23]
	set tuple_temp_155 = Colors_PLAYER_COLORS_green[23]
	set tuple_temp_156 = Colors_PLAYER_COLORS_blue[23]
	set Colors_hexs[0] = "0"
	set Colors_hexs[1] = "1"
	set Colors_hexs[2] = "2"
	set Colors_hexs[3] = "3"
	set Colors_hexs[4] = "4"
	set Colors_hexs[5] = "5"
	set Colors_hexs[6] = "6"
	set Colors_hexs[7] = "7"
	set Colors_hexs[8] = "8"
	set Colors_hexs[9] = "9"
	set Colors_hexs[10] = "A"
	set Colors_hexs[11] = "B"
	set Colors_hexs[12] = "C"
	set Colors_hexs[13] = "D"
	set Colors_hexs[14] = "E"
	set Colors_hexs[15] = "F"
	set Colors_decs = new_Table()
	call initializeTable()
	return true
endfunction

function onEnter takes code c returns nothing
	call trigger_addCondition(OnUnitEnterLeave_eventTrigger, Filter(c))
endfunction

function timer_startPeriodic takes timer this_2, real time, code timerCallBack returns nothing
	call TimerStart(this_2, time, true, timerCallBack)
endfunction

function init_DamageDetection takes nothing returns boolean
	local integer i_2
	local integer temp
	set DamageDetection_SWAP_TIMEOUT = 600.
	set DamageDetection_funcNext = 0
	set DamageDetection_current = null
	set DamageDetection_toDestroy = null
	set DamageDetection_filter = Filter(ref_function_code__Filter_DamageDetection)
	set DamageDetection_current = CreateTrigger()
	set i_2 = 0
	set temp = DamageDetection_funcNext - 1
	loop
		exitwhen i_2 > temp
		call trigger_addCondition(DamageDetection_current, DamageDetection_func[i_2])
		set i_2 = i_2 + 1
	endloop
	call onEnter(ref_function_code__onEnter_DamageDetection)
	call timer_startPeriodic(CreateTimer(), DamageDetection_SWAP_TIMEOUT, ref_function_swap)
	return true
endfunction

function EventListener_add takes eventid eventId, integer listener returns integer
	set EventListener_eventId[listener] = eventid_toIntId(eventId)
	if EventListener_generalListenersFirsts[EventListener_eventId[listener]] != 0 then
		set EventListener_prev[EventListener_generalListenersFirsts[EventListener_eventId[listener]]] = listener
		set EventListener_next[listener] = EventListener_generalListenersFirsts[EventListener_eventId[listener]]
	endif
	set EventListener_generalListenersFirsts[EventListener_eventId[listener]] = listener
	return listener
endfunction

function alloc_EventListener_add_DamageEvent takes nothing returns integer
	local integer this_2
	if EventListener_firstFree == 0 then
		if EventListener_maxIndex < 32768 then
			set EventListener_maxIndex = EventListener_maxIndex + 1
			set this_2 = EventListener_maxIndex
			set EventListener_typeId[this_2] = 679
		else
			call error("Out of memory: Could not create EventListener_add_DamageEvent.")
			set this_2 = 0
		endif
	else
		set EventListener_firstFree = EventListener_firstFree - 1
		set this_2 = EventListener_nextFree[EventListener_firstFree]
		set EventListener_typeId[this_2] = 679
	endif
	return this_2
endfunction

function construct_EventListener takes integer this_2 returns nothing
	set EventListener_eventId[this_2] = 0
	set EventListener_next[this_2] = 0
	set EventListener_prev[this_2] = 0
endfunction

function alloc_DamageElement takes nothing returns integer
	local integer this_2
	if DamageElement_firstFree == 0 then
		if DamageElement_maxIndex < 32768 then
			set DamageElement_maxIndex = DamageElement_maxIndex + 1
			set this_2 = DamageElement_maxIndex
		else
			call error("Out of memory: Could not create DamageElement.")
			set this_2 = 0
		endif
	else
		set DamageElement_firstFree = DamageElement_firstFree - 1
		set this_2 = DamageElement_nextFree[DamageElement_firstFree]
	endif
	return this_2
endfunction

function construct_DamageElement takes integer this_2, string name, integer color_red, integer color_green, integer color_blue, integer color_alpha returns nothing
	local integer tuple_temp = Colors_COLOR_WHITE_red
	local integer tuple_temp_2 = Colors_COLOR_WHITE_green
	local integer tuple_temp_3 = Colors_COLOR_WHITE_blue
	local integer tuple_temp_4 = Colors_COLOR_WHITE_alpha
	local integer tuple_temp_5 = color_red
	local integer tuple_temp_6 = color_green
	local integer tuple_temp_7 = color_blue
	local integer tuple_temp_8 = color_alpha
endfunction

function new_DamageElement takes string name, integer color_red, integer color_green, integer color_blue, integer color_alpha returns integer
	local integer this_2 = alloc_DamageElement()
	call construct_DamageElement(this_2, name, color_red, color_green, color_blue, color_alpha)
	return this_2
endfunction

function init_DamageEvent takes nothing returns boolean
	local integer clVar
	local unitevent temp
	set DamageEvent_DETECT_NATIVE_ABILITIES = false
	set DamageEvent_DAMAGE_ELEMENT_ATTACK = new_DamageElement("Physical", 223, 59, 33, 255)
	set DamageEvent_DETECT_NATIVE_ABILITIES_ID = 1095577650
	set DamageInstance_count = 0
	set DamageEvent_nextDamageId = 0
	set DamageEvent_nextDamageType = 4
	set DamageEvent_nextDamageElement = 0
	set DamageEvent_abort = false
	set DamageEvent_maxPriority = 0
	set temp = EVENT_UNIT_DAMAGED
	set clVar = alloc_EventListener_add_DamageEvent()
	call construct_EventListener(clVar)
	call EventListener_add(temp, clVar)
	if DamageEvent_DETECT_NATIVE_ABILITIES then
		call onEnter(ref_function_code__onEnter_DamageEvent)
	endif
	set temp = null
	return true
endfunction

function init_DebugFile takes nothing returns boolean
	call PreloadGenClear()
	return true
endfunction

function init_DialogBox takes nothing returns boolean
	set DialogBox_buttonClosures = new_HashMap()
	return true
endfunction

function init_Doodads takes nothing returns boolean
	return true
endfunction

function createDummy takes real pos_x, real pos_y, player owner, real facing_radians returns unit
	local unit u = createUnit(owner, DummyRecycler_DUMMY_UNIT_ID, pos_x, pos_y, facing_radians)
	local unit receiver = u
	local unit receiver_2
	local unit receiver_3
	local unit receiver_4
	call unit_setXY(receiver, pos_x, pos_y)
	set receiver_2 = receiver
	call unit_addAbility(receiver_2, Basics_HEIGHT_ENABLER)
	set receiver_3 = receiver_2
	call unit_removeAbility(receiver_3, Basics_HEIGHT_ENABLER)
	set receiver_4 = receiver_3
	call unit_addAbility(receiver_4, Basics_LOCUST_ID)
	set createDummytempReturn = u
	set u = null
	set receiver = null
	set receiver_2 = null
	set receiver_3 = null
	set receiver_4 = null
	return createDummytempReturn
endfunction

function alloc_ArrayQueue takes nothing returns integer
	local integer this_2
	if ArrayQueue_firstFree == 0 then
		if ArrayQueue_maxIndex < 32768 then
			set ArrayQueue_maxIndex = ArrayQueue_maxIndex + 1
			set this_2 = ArrayQueue_maxIndex
			set ArrayQueue_typeId[this_2] = 631
		else
			call error("Out of memory: Could not create ArrayQueue.")
			set this_2 = 0
		endif
	else
		set ArrayQueue_firstFree = ArrayQueue_firstFree - 1
		set this_2 = ArrayQueue_nextFree[ArrayQueue_firstFree]
		set ArrayQueue_typeId[this_2] = 631
	endif
	return this_2
endfunction

function construct_ArrayQueue takes integer this_2 returns nothing
	set ArrayQueue_rp[this_2] = 0
	set ArrayQueue_size[this_2] = 0
endfunction

function new_ArrayQueue takes nothing returns integer
	local integer this_2 = alloc_ArrayQueue()
	call construct_ArrayQueue(this_2)
	return this_2
endfunction

function init_DummyRecycler takes nothing returns boolean
	local integer i_2
	local integer temp
	local integer j
	local integer temp_2
	set DummyRecycler_DUMMY_UNIT_ID = 2019849581
	set DummyRecycler_DIFFERENT_ANGLES = 8
	set DummyRecycler_ANGLE_DEGREE = 360 * 1. / DummyRecycler_DIFFERENT_ANGLES
	set DummyRecycler_SAVED_UNITS_PER_ANGLE = 6
	set DelayNode_t = CreateTimer()
	set DelayNode_first = 0
	set i_2 = 0
	set temp = DummyRecycler_DIFFERENT_ANGLES - 1
	loop
		exitwhen i_2 > temp
		set DummyRecycler_angleQueues[i_2] = new_ArrayQueue()
		set j = 0
		set temp_2 = DummyRecycler_SAVED_UNITS_PER_ANGLE - 1
		loop
			exitwhen j > temp_2
			call dispatch_ArrayQueue_DummyRecycler_ArrayQueue_enqueue(DummyRecycler_angleQueues[i_2], createDummy(MapBounds_boundMax_x, MapBounds_boundMax_y, Basics_DUMMY_PLAYER, real_asAngleDegrees(i_2 * DummyRecycler_ANGLE_DEGREE)))
			set j = j + 1
		endloop
		set i_2 = i_2 + 1
	endloop
	return true
endfunction

function init_ErrorHandling takes nothing returns boolean
	set ErrorHandling_MUTE_ERROR_DURATION = 60
	set ErrorHandling_PRIMARY_ERROR_KEY = -1
	set ErrorHandling_HT = InitHashtable()
	set ErrorHandling_lastError = ""
	set ErrorHandling_suppressErrorMessages = false
	return true
endfunction

function init_EventHelper takes nothing returns boolean
	call new_HashMap()
	call new_HashMap()
	call new_Table()
	return true
endfunction

function force_addPlayer takes force this_2, player whichPlayer returns nothing
	call ForceAddPlayer(this_2, whichPlayer)
endfunction

function init_Execute takes nothing returns boolean
	set Execute_executeForce = CreateForce()
	call force_addPlayer(Execute_executeForce, Player_localPlayer)
	set Execute_tempCallbacksCount = 0
	return true
endfunction

function init_ForceTests takes nothing returns boolean
	set ForceTests_testInt = 0
	return true
endfunction

function init_GameTimer takes nothing returns boolean
	set GameTimer_gameTimer = CreateTimer()
	call timer_start(GameTimer_gameTimer, 100000., null)
	call timer_startPeriodic(CreateTimer(), Basics_ANIMATION_PERIOD, ref_function_code__startPeriodic_GameTimer)
	return true
endfunction

function alloc_ForForceCallback_execute_GamecacheKeys takes nothing returns integer
	local integer this_2
	if ForForceCallback_firstFree == 0 then
		if ForForceCallback_maxIndex < 32768 then
			set ForForceCallback_maxIndex = ForForceCallback_maxIndex + 1
			set this_2 = ForForceCallback_maxIndex
			set ForForceCallback_typeId[this_2] = 697
		else
			call error("Out of memory: Could not create ForForceCallback_execute_GamecacheKeys.")
			set this_2 = 0
		endif
	else
		set ForForceCallback_firstFree = ForForceCallback_firstFree - 1
		set this_2 = ForForceCallback_nextFree[ForForceCallback_firstFree]
		set ForForceCallback_typeId[this_2] = 697
	endif
	return this_2
endfunction

function init_GamecacheKeys takes nothing returns boolean
	local integer i_2
	local integer temp
	local integer clVar
	set GamecacheKeys_GC_KEYS = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`{|}~"
	set GamecacheKeys_GC_KEYS_LENGTH = 69
	set GamecacheKeys_count = 0
	set i_2 = 1
	set temp = NetworkConfig_MAX_CHARACTERS_PER_KEY - 1
	loop
		exitwhen i_2 > temp
		set clVar = alloc_ForForceCallback_execute_GamecacheKeys()
		set i_649[clVar] = i_2
		call execute(clVar)
		set i_2 = i_2 + 1
	endloop
	return true
endfunction

function init_Group takes nothing returns boolean
	set Group_ENUM_GROUP = CreateGroup()
	set Group_unitCounter = 0
	set Group_randomCounter = 0
	return true
endfunction

function alloc_ForForceCallback_execute_GroupUtils takes nothing returns integer
	local integer this_2
	if ForForceCallback_firstFree == 0 then
		if ForForceCallback_maxIndex < 32768 then
			set ForForceCallback_maxIndex = ForForceCallback_maxIndex + 1
			set this_2 = ForForceCallback_maxIndex
			set ForForceCallback_typeId[this_2] = 699
		else
			call error("Out of memory: Could not create ForForceCallback_execute_GroupUtils.")
			set this_2 = 0
		endif
	else
		set ForForceCallback_firstFree = ForForceCallback_firstFree - 1
		set this_2 = ForForceCallback_nextFree[ForForceCallback_firstFree]
		set ForForceCallback_typeId[this_2] = 699
	endif
	return this_2
endfunction

function max_2 takes integer numbers_0, integer numbers_1 returns integer
	local integer maxNumber = Integer_INT_MIN
	local integer cond_result
	if numbers_0 > maxNumber then
		set cond_result = numbers_0
	else
		set cond_result = maxNumber
	endif
	set maxNumber = cond_result
	if numbers_1 > maxNumber then
		set cond_result = numbers_1
	else
		set cond_result = maxNumber
	endif
	set maxNumber = cond_result
	return maxNumber
endfunction

function createGroups takes integer number returns nothing
	local integer maxCreatePerCycle = 256
	local integer actualLimit = max_2(JASS_MAX_ARRAY_SIZE - 1, GroupUtils_GROUP_NUMBER_LIMIT)
	local integer numTarget = GroupUtils_numTotal + number
	local integer overflow = max_2(0, numTarget - actualLimit)
	local integer toCreate = max_2(0, numTarget - overflow)
	local integer createNow_2
	local integer clVar
	loop
		exitwhen  not (toCreate > 0)
		set createNow_2 = min_2(toCreate, maxCreatePerCycle)
		set toCreate = toCreate - createNow_2
		set clVar = alloc_ForForceCallback_execute_GroupUtils()
		set createNow[clVar] = createNow_2
		call execute(clVar)
	endloop
	if GroupUtils_numTotal >= GroupUtils_GROUP_NUMBER_LIMIT and ( not GroupUtils_shownMaxError) then
		call Log_warn("Maximum number of GroupUtils groups (" + int_toString(GroupUtils_GROUP_NUMBER_LIMIT) + ") created. " + "All newly created groups will be non-recyclable.")
		set GroupUtils_shownMaxError = true
	endif
endfunction

function initialize takes nothing returns nothing
	call createGroups(GroupUtils_START_CREATE_GROUPS)
endfunction

function init_GroupUtils takes nothing returns boolean
	set GroupUtils_GROUP_NUMBER_LIMIT = 1024
	set GroupUtils_START_CREATE_GROUPS = 64
	set GroupUtils_used = new_HashMap()
	set GroupUtils_numStack = 0
	set GroupUtils_numTotal = 0
	set GroupUtils_shownMaxError = false
	call initialize()
	return true
endfunction

function init_HashList takes nothing returns boolean
	set HashList_ht = InitHashtable()
	set HashList_occurences = InitHashtable()
	return true
endfunction

function init_HashSet takes nothing returns boolean
	set HashSet_position = InitHashtable()
	return true
endfunction

function alloc_BackIterator takes nothing returns integer
	local integer this_2
	if BackIterator_firstFree == 0 then
		if BackIterator_maxIndex < 32768 then
			set BackIterator_maxIndex = BackIterator_maxIndex + 1
			set this_2 = BackIterator_maxIndex
		else
			call error("Out of memory: Could not create BackIterator.")
			set this_2 = 0
		endif
	else
		set BackIterator_firstFree = BackIterator_firstFree - 1
		set this_2 = BackIterator_nextFree[BackIterator_firstFree]
	endif
	return this_2
endfunction

function construct_BackIterator takes integer this_2, boolean destroyOnClose returns nothing
endfunction

function new_BackIterator takes boolean destroyOnClose returns integer
	local integer this_2 = alloc_BackIterator()
	call construct_BackIterator(this_2, destroyOnClose)
	return this_2
endfunction

function init_IOTaskExecutor takes nothing returns boolean
	set TimedIOTaskExecutor_LinkedListModule_first = 0
	set TimedIOTaskExecutor_LinkedListModule_last = 0
	set TimedIOTaskExecutor_LinkedListModule_size = 0
	call new_Iterator(false)
	call new_BackIterator(false)
	set TimedIOTaskExecutor_updater = getTimer()
	set TimedIOTaskExecutor_runningCount = 0
	set TimedIOTaskExecutor_timerStarted = false
	call timer_startPeriodic(TimedIOTaskExecutor_updater, Basics_ANIMATION_PERIOD, ref_function_TimedIOTaskExecutor_updateInstances)
	call timer_pause(TimedIOTaskExecutor_updater)
	return true
endfunction

function init_Icons takes nothing returns boolean
	return true
endfunction

function unit_setPathing takes unit this_2, boolean value_2 returns nothing
	call SetUnitPathing(this_2, value_2)
endfunction

function init_InstantDummyCaster takes nothing returns boolean
	local unit receiver
	local unit receiver_2
	local unit receiver_3
	set InstantDummyCaster_DUMMY_CASTER_UNIT_ID = 2016423985
	set receiver = createUnit(Basics_DUMMY_PLAYER, InstantDummyCaster_DUMMY_CASTER_UNIT_ID, MapBounds_boundMax_x, MapBounds_boundMax_y, 0.)
	call unit_setPathing(receiver, false)
	set receiver_2 = receiver
	call unit_addAbility(receiver_2, Basics_HEIGHT_ENABLER)
	set receiver_3 = receiver_2
	call unit_removeAbility(receiver_3, Basics_HEIGHT_ENABLER)
	set receiver = null
	set receiver_2 = null
	set receiver_3 = null
	return true
endfunction

function init_Integer takes nothing returns boolean
	set Integer_INT_MAX = 2147483647
	set Integer_INT_MIN = -2147483648
	return true
endfunction

function alloc_BackIterator_469 takes nothing returns integer
	local integer this_2
	if BackIterator_firstFree_27 == 0 then
		if BackIterator_maxIndex_31 < 32768 then
			set BackIterator_maxIndex_31 = BackIterator_maxIndex_31 + 1
			set this_2 = BackIterator_maxIndex_31
		else
			call error("Out of memory: Could not create BackIterator.")
			set this_2 = 0
		endif
	else
		set BackIterator_firstFree_27 = BackIterator_firstFree_27 - 1
		set this_2 = BackIterator_nextFree_35[BackIterator_firstFree_27]
	endif
	return this_2
endfunction

function construct_BackIterator_629 takes integer this_2, boolean destroyOnClose returns nothing
endfunction

function new_BackIterator_1179 takes boolean destroyOnClose returns integer
	local integer this_2 = alloc_BackIterator_469()
	call construct_BackIterator_629(this_2, destroyOnClose)
	return this_2
endfunction

function alloc_Iterator_502 takes nothing returns integer
	local integer this_2
	if Iterator_firstFree_270 == 0 then
		if Iterator_maxIndex_274 < 32768 then
			set Iterator_maxIndex_274 = Iterator_maxIndex_274 + 1
			set this_2 = Iterator_maxIndex_274
			set Iterator_typeId_282[this_2] = 740
		else
			call error("Out of memory: Could not create Iterator.")
			set this_2 = 0
		endif
	else
		set Iterator_firstFree_270 = Iterator_firstFree_270 - 1
		set this_2 = Iterator_nextFree_278[Iterator_firstFree_270]
		set Iterator_typeId_282[this_2] = 740
	endif
	return this_2
endfunction

function construct_Iterator_640 takes integer this_2, boolean destroyOnClose returns nothing
	set Iterator_current_265[this_2] = Knockback3_LinkedListModule_first
endfunction

function new_Iterator_1188 takes boolean destroyOnClose returns integer
	local integer this_2 = alloc_Iterator_502()
	call construct_Iterator_640(this_2, destroyOnClose)
	return this_2
endfunction

function init_Knockback3 takes nothing returns boolean
	set Knockback3_DESTRUCTABLE_ENUM_SIZE = 130.
	set Knockback3_USE_MOVE_SPEED_MODIFIERS = true
	set Knockback3_USE_PROP_WINDOW_MODIFIERS = true
	set Knockback3_LinkedListModule_first = 0
	set Knockback3_LinkedListModule_last = 0
	set Knockback3_LinkedListModule_size = 0
	set Knockback3_LinkedListModule_staticItr = new_Iterator_1188(false)
	call new_BackIterator_1179(false)
	set Knockback3_restitutionCoefficientGround = .2
	set Knockback3_restitutionCoefficientDestructable = .3
	set Knockback3_frictionCoefficientGround = .15
	set Knockback3_gravity = 90.
	set Knockback3_elasticityThreshold =  - 300.
	set Knockback3_airborneThreshold = 150.
	set Knockback3_isAirborneThreshold = 5.
	set Knockback3_minimumSlideSpeed = 30.
	set Knockback3_frictionFxThreshold = 180.
	set Knockback3_frictionFxPath = Objects_impaleTargetDust
	set Knockback3_destroyDestructableSpeedThreshold = 300.
	set Knockback3_destroyDestructableHeightThreshold = 150.
	set Knockback3_destructableRect = Rect( - (1. * Knockback3_DESTRUCTABLE_ENUM_SIZE),  - (1. * Knockback3_DESTRUCTABLE_ENUM_SIZE), Knockback3_DESTRUCTABLE_ENUM_SIZE, Knockback3_DESTRUCTABLE_ENUM_SIZE)
	set Knockback3_unitNodes = new_HashMap()
	set Knockback3_clock = CreateTimer()
	return true
endfunction

function registerPlayerUnitEvent_1253 takes playerunitevent p, code filter, code c returns nothing
	call registerPlayerUnitEvent_1254(p, filter, c, null)
endfunction

function init_LastOrder takes nothing returns boolean
	set LastOrder_ORDERS_TO_HOLD = 3
	call registerPlayerUnitEvent_1253(EVENT_PLAYER_UNIT_ISSUED_TARGET_ORDER, null, ref_function_actions)
	call registerPlayerUnitEvent_1253(EVENT_PLAYER_UNIT_ISSUED_POINT_ORDER, null, ref_function_actions)
	call registerPlayerUnitEvent_1253(EVENT_PLAYER_UNIT_ISSUED_ORDER, null, ref_function_actions)
	call registerPlayerUnitEvent_1253(EVENT_PLAYER_UNIT_SPELL_EFFECT, null, ref_function_spellActions)
	call onUnitDeindex(ref_function_code__onUnitDeindex_LastOrder)
	return true
endfunction

function init_Lightning takes nothing returns boolean
	return true
endfunction

function alloc_Comparator_LinkedList takes nothing returns integer
	local integer this_2
	if Comparator_firstFree == 0 then
		if Comparator_maxIndex < 32768 then
			set Comparator_maxIndex = Comparator_maxIndex + 1
			set this_2 = Comparator_maxIndex
		else
			call error("Out of memory: Could not create Comparator_LinkedList.")
			set this_2 = 0
		endif
	else
		set Comparator_firstFree = Comparator_firstFree - 1
		set this_2 = Comparator_nextFree[Comparator_firstFree]
	endif
	return this_2
endfunction

function alloc_Comparator_LinkedList_478 takes nothing returns integer
	local integer this_2
	if Comparator_firstFree == 0 then
		if Comparator_maxIndex < 32768 then
			set Comparator_maxIndex = Comparator_maxIndex + 1
			set this_2 = Comparator_maxIndex
		else
			call error("Out of memory: Could not create Comparator_LinkedList.")
			set this_2 = 0
		endif
	else
		set Comparator_firstFree = Comparator_firstFree - 1
		set this_2 = Comparator_nextFree[Comparator_firstFree]
	endif
	return this_2
endfunction

function realToIndex takes real r returns integer
	return real_toInt(r * TypeCasting_R2I_PRECISION)
endfunction

function init_LinkedList takes nothing returns boolean
	local integer clVar = alloc_Comparator_LinkedList()
	local integer clVar_2 = alloc_Comparator_LinkedList_478()
	call realToIndex(0.)
	return true
endfunction

function alloc_BackIterator_470 takes nothing returns integer
	local integer this_2
	if BackIterator_firstFree_28 == 0 then
		if BackIterator_maxIndex_32 < 32768 then
			set BackIterator_maxIndex_32 = BackIterator_maxIndex_32 + 1
			set this_2 = BackIterator_maxIndex_32
		else
			call error("Out of memory: Could not create BackIterator.")
			set this_2 = 0
		endif
	else
		set BackIterator_firstFree_28 = BackIterator_firstFree_28 - 1
		set this_2 = BackIterator_nextFree_36[BackIterator_firstFree_28]
	endif
	return this_2
endfunction

function construct_BackIterator_630 takes integer this_2, boolean destroyOnClose returns nothing
endfunction

function new_BackIterator_1180 takes boolean destroyOnClose returns integer
	local integer this_2 = alloc_BackIterator_470()
	call construct_BackIterator_630(this_2, destroyOnClose)
	return this_2
endfunction

function alloc_Iterator_503 takes nothing returns integer
	local integer this_2
	if Iterator_firstFree_271 == 0 then
		if Iterator_maxIndex_275 < 32768 then
			set Iterator_maxIndex_275 = Iterator_maxIndex_275 + 1
			set this_2 = Iterator_maxIndex_275
		else
			call error("Out of memory: Could not create Iterator.")
			set this_2 = 0
		endif
	else
		set Iterator_firstFree_271 = Iterator_firstFree_271 - 1
		set this_2 = Iterator_nextFree_279[Iterator_firstFree_271]
	endif
	return this_2
endfunction

function construct_Iterator_641 takes integer this_2, boolean destroyOnClose returns nothing
endfunction

function new_Iterator_1189 takes boolean destroyOnClose returns integer
	local integer this_2 = alloc_Iterator_503()
	call construct_Iterator_641(this_2, destroyOnClose)
	return this_2
endfunction

function init_LinkedListModuleTests takes nothing returns boolean
	set TestClass_LinkedListModule_first = 0
	set TestClass_LinkedListModule_last = 0
	call new_Iterator_1189(false)
	call new_BackIterator_1180(false)
	return true
endfunction

function alloc_CallbackSingle_doAfter_LocalFiles takes nothing returns integer
	local integer this_2
	if CallbackSingle_firstFree == 0 then
		if CallbackSingle_maxIndex < 32768 then
			set CallbackSingle_maxIndex = CallbackSingle_maxIndex + 1
			set this_2 = CallbackSingle_maxIndex
			set CallbackSingle_typeId[this_2] = 658
		else
			call error("Out of memory: Could not create CallbackSingle_doAfter_LocalFiles.")
			set this_2 = 0
		endif
	else
		set CallbackSingle_firstFree = CallbackSingle_firstFree - 1
		set this_2 = CallbackSingle_nextFree[CallbackSingle_firstFree]
		set CallbackSingle_typeId[this_2] = 658
	endif
	return this_2
endfunction

function init_LocalFiles takes nothing returns boolean
	local integer clVar
	set LocalFiles_callbacks = new_LinkedList()
	set clVar = alloc_CallbackSingle_doAfter_LocalFiles()
	call construct_CallbackSingle(clVar)
	call doAfter(1., clVar)
	return true
endfunction

function init_MagicFunctions takes nothing returns boolean
	set MagicFunctions_compiletime = false
	return true
endfunction

function rect_getMaxX takes rect this_2 returns real
	return GetRectMaxX(this_2)
endfunction

function rect_getMaxY takes rect this_2 returns real
	return GetRectMaxY(this_2)
endfunction

function rect_getMinX takes rect this_2 returns real
	return GetRectMinX(this_2)
endfunction

function rect_getMinY takes rect this_2 returns real
	return GetRectMinY(this_2)
endfunction

function region_addRect takes region this_2, rect rct returns nothing
	call RegionAddRect(this_2, rct)
endfunction

function vec2_op_mult takes real this_x, real this_y, real factor returns real
	set vec2_op_mult_return_x = this_x * factor
	set vec2_op_mult_return_y = this_y * factor
	return vec2_op_mult_return_x
endfunction

function vec2_op_plus takes real this_x, real this_y, real v_x, real v_y returns real
	set vec2_op_plus_return_x = this_x + v_x
	set vec2_op_plus_return_y = this_y + v_y
	return vec2_op_plus_return_x
endfunction

function initMapBounds takes nothing returns nothing
	local rect cond_result
	local region receiver
	local rect cond_result_2
	local region receiver_2
	local real tuple_temp
	local real tuple_temp_2
	local real tuple_temp_3
	local real tuple_temp_4
	local real tuple_temp_5
	local real tuple_temp_6
	local real tuple_temp_7
	local real tuple_temp_8
	local real tuple_temp_9
	local real tuple_temp_10
	local real tuple_temp_11
	local real tuple_temp_12
	if MagicFunctions_compiletime then
		set cond_result = Rect(-1024., -1024., 1024., 1024.)
	else
		set cond_result = GetPlayableMapRect()
	endif
	set MapBounds_playableMapRect = cond_result
	set receiver = CreateRegion()
	call region_addRect(receiver, MapBounds_playableMapRect)
	if MagicFunctions_compiletime then
		set cond_result_2 = Rect(-1536., -1536., 1536., 1536.)
	else
		set cond_result_2 = GetWorldBounds()
	endif
	set MapBounds_boundRect = cond_result_2
	set receiver_2 = CreateRegion()
	call region_addRect(receiver_2, MapBounds_boundRect)
	set MapBounds_boundRegion = receiver_2
	set tuple_temp = rect_getMinX(MapBounds_playableMapRect)
	set tuple_temp_2 = rect_getMinY(MapBounds_playableMapRect)
	set MapBounds_playableMin_x = tuple_temp
	set MapBounds_playableMin_y = tuple_temp_2
	set tuple_temp_3 = rect_getMaxX(MapBounds_playableMapRect)
	set tuple_temp_4 = rect_getMaxY(MapBounds_playableMapRect)
	set MapBounds_playableMax_x = tuple_temp_3
	set MapBounds_playableMax_y = tuple_temp_4
	set tuple_temp_5 = rect_getMinX(MapBounds_boundRect)
	set tuple_temp_6 = rect_getMinY(MapBounds_boundRect)
	set MapBounds_boundMin_x = tuple_temp_5
	set MapBounds_boundMin_y = tuple_temp_6
	set tuple_temp_7 = rect_getMaxX(MapBounds_boundRect)
	set tuple_temp_8 = rect_getMaxY(MapBounds_boundRect)
	set MapBounds_boundMax_x = tuple_temp_7
	set MapBounds_boundMax_y = tuple_temp_8
	set tuple_temp_9 = vec2_op_mult(vec2_op_plus(MapBounds_playableMin_x, MapBounds_playableMin_y, MapBounds_playableMax_x, MapBounds_playableMax_y), vec2_op_plus_return_y, .5)
	set tuple_temp_10 = vec2_op_mult_return_y
	set tuple_temp_11 = vec2_op_mult(vec2_op_plus(MapBounds_boundMin_x, MapBounds_boundMin_y, MapBounds_boundMax_x, MapBounds_boundMax_y), vec2_op_plus_return_y, .5)
	set tuple_temp_12 = vec2_op_mult_return_y
endfunction

function init_MapBounds takes nothing returns boolean
	call initMapBounds()
	return true
endfunction

function init_Maths takes nothing returns boolean
	return true
endfunction

function init_Matrices takes nothing returns boolean
	local real tuple_temp = 0.
	local real tuple_temp_2 = 0.
	local real tuple_temp_3 = 0.
	local real tuple_temp_4 = 0.
	local real tuple_temp_5 = 1.
	local real tuple_temp_6 = 0.
	local real tuple_temp_7 = 0.
	local real tuple_temp_8 = 1.
	local real tuple_temp_9 = 0.
	local real tuple_temp_10 = 0.
	local real tuple_temp_11 = 0.
	local real tuple_temp_12 = 0.
	local real tuple_temp_13 = 0.
	local real tuple_temp_14 = 0.
	local real tuple_temp_15 = 0.
	local real tuple_temp_16 = 0.
	local real tuple_temp_17 = 0.
	local real tuple_temp_18 = 1.
	local real tuple_temp_19 = 0.
	local real tuple_temp_20 = 0.
	local real tuple_temp_21 = 0.
	local real tuple_temp_22 = 1.
	local real tuple_temp_23 = 0.
	local real tuple_temp_24 = 0.
	local real tuple_temp_25 = 0.
	local real tuple_temp_26 = 1.
	return true
endfunction

function init_Metadata takes nothing returns boolean
	local integer tuple_temp = 0
	local integer tuple_temp_2 = 0
	local integer tuple_temp_3 = 0
	local integer tuple_temp_4 = 0
	local integer tuple_temp_5 = 0
	set MetadataStore_META_CACHE = InitGameCache("m.w3v")
	set MetadataStore_META_INTEGER_KEY = GamecacheKeys_get(0)
	set MetadataStore_META_REAL_KEY = GamecacheKeys_get(1)
	set MetadataStore_META_BOOLEAN_KEY = GamecacheKeys_get(2)
	set MetadataStore_META_STRING_KEY = GamecacheKeys_get(3)
	set MetadataStore_META_SYNC_ROUNDS_KEY = GamecacheKeys_get(4)
	set MetadataStore_META_COUNT = 5
	return true
endfunction

function init_Network takes nothing returns boolean
	set Network_DATA_CACHE = InitGameCache("1")
	set Network_STRING_CACHE = InitGameCache("2")
	return true
endfunction

function init_NetworkConfig takes nothing returns boolean
	set NetworkConfig_CHARS_PER_ENCODE_DECODE = 256
	set NetworkConfig_DATA_PER_EXECUTE = 128
	set NetworkConfig_MAX_CHARACTERS_PER_KEY = 3
	return true
endfunction

function alloc_IdGenerator takes nothing returns integer
	local integer this_2
	if IdGenerator_firstFree == 0 then
		if IdGenerator_maxIndex < 32768 then
			set IdGenerator_maxIndex = IdGenerator_maxIndex + 1
			set this_2 = IdGenerator_maxIndex
		else
			call error("Out of memory: Could not create IdGenerator.")
			set this_2 = 0
		endif
	else
		set IdGenerator_firstFree = IdGenerator_firstFree - 1
		set this_2 = IdGenerator_nextFree[IdGenerator_firstFree]
	endif
	return this_2
endfunction

function construct_IdGenerator takes integer this_2, integer start returns nothing
endfunction

function new_IdGenerator takes integer start returns integer
	local integer this_2 = alloc_IdGenerator()
	call construct_IdGenerator(this_2, start)
	return this_2
endfunction

function init_ObjectIdGenerator takes nothing returns boolean
	call new_IdGenerator(2016423984)
	call new_IdGenerator(1213018160)
	call new_IdGenerator(1095577648)
	call new_IdGenerator(1112354864)
	call new_IdGenerator(1229795376)
	call new_IdGenerator(1380790320)
	return true
endfunction

function init_ObjectIds takes nothing returns boolean
	set ObjectIds_CHARMAP = ".................................!.#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[.]^_`abcdefghijklmnopqrstuvwxyz{|}~................................................................................................................................."
	return true
endfunction

function init_Objects takes nothing returns boolean
	set Objects_impaleTargetDust = "Objects\\Spawnmodels\\Undead\\ImpaleTargetDust\\ImpaleTargetDust.mdx"
	return true
endfunction

function alloc_CallbackSingle_nullTimer_OnUnitEnterLeave takes nothing returns integer
	local integer this_2
	if CallbackSingle_firstFree == 0 then
		if CallbackSingle_maxIndex < 32768 then
			set CallbackSingle_maxIndex = CallbackSingle_maxIndex + 1
			set this_2 = CallbackSingle_maxIndex
			set CallbackSingle_typeId[this_2] = 660
		else
			call error("Out of memory: Could not create CallbackSingle_nullTimer_OnUnitEnterLeave.")
			set this_2 = 0
		endif
	else
		set CallbackSingle_firstFree = CallbackSingle_firstFree - 1
		set this_2 = CallbackSingle_nextFree[CallbackSingle_firstFree]
		set CallbackSingle_typeId[this_2] = 660
	endif
	return this_2
endfunction

function player_setAbilityAvailable takes player this_2, integer abilityId, boolean avail returns nothing
	call SetPlayerAbilityAvailable(this_2, abilityId, avail)
endfunction

function init_OnUnitEnterLeave takes nothing returns boolean
	local integer i_2
	local integer temp
	local integer clVar
	set OnUnitEnterLeave_eventTrigger = CreateTrigger()
	set OnUnitEnterLeave_preplacedUnits = CreateGroup()
	set OnUnitEnterLeave_tempUnitsCount = 0
	set OnUnitEnterLeave_ABILITY_ID = 1095577649
	set i_2 = 0
	set temp = bj_MAX_PLAYER_SLOTS - 1
	loop
		exitwhen i_2 > temp
		call player_setAbilityAvailable(Player_players[i_2], OnUnitEnterLeave_ABILITY_ID, false)
		set i_2 = i_2 + 1
	endloop
	set clVar = alloc_CallbackSingle_nullTimer_OnUnitEnterLeave()
	call construct_CallbackSingle(clVar)
	call nullTimer(clVar)
	return true
endfunction

function init_OrderedStringBuffer takes nothing returns boolean
	set OrderedStringBuffer_TERMINATOR = "|"
	return true
endfunction

function init_Orders takes nothing returns boolean
	return true
endfunction

function init_Persistable takes nothing returns boolean
	return true
endfunction

function initPlayerArray takes nothing returns nothing
	local integer i_2 = 0
	local integer temp = bj_MAX_PLAYER_SLOTS - 1
	loop
		exitwhen i_2 > temp
		set Player_players[i_2] = Player(i_2)
		set i_2 = i_2 + 1
	endloop
endfunction

function init_Player takes nothing returns boolean
	set Player_localPlayer = GetLocalPlayer()
	call initPlayerArray()
	return true
endfunction

function init_Playercolor takes nothing returns boolean
	set Playercolor_PLAYER_COLOR_BLACK_AGGRESSIVE = ConvertPlayerColor(24)
	set Playercolor_PLAYER_COLOR_UNKNOWN1 = ConvertPlayerColor(25)
	set Playercolor_PLAYER_COLOR_UNKNOWN2 = ConvertPlayerColor(26)
	set Playercolor_PLAYER_COLOR_BLACK_PASSIVE = ConvertPlayerColor(27)
	return true
endfunction

function init_PreloadIO takes nothing returns boolean
	set PreloadIO_PACKETS_PER_FILE = bj_MAX_PLAYER_SLOTS
	set PreloadIO_MAX_PACKET_LENGTH = 222
	set IOWriter_DATA_PADDING_1 = "\")\r\n\tcall SetPlayerName(Player("
	set IOWriter_DATA_PADDING_2 = "), \""
	set IOWriter_DATA_PADDING_3 = "\")\r\n//"
	set IOWriter_DATA_FOOTER = "\" )\r\nendfunction\r\nfunction AAA takes nothing returns nothing \r\n//"
	set IOWriter_packetNumber = 0
	set IOReader_packetNumber = 0
	set IOReader_packetCount = 0
	return true
endfunction

function alloc_CallbackSingle_nullTimer_Preloader takes nothing returns integer
	local integer this_2
	if CallbackSingle_firstFree == 0 then
		if CallbackSingle_maxIndex < 32768 then
			set CallbackSingle_maxIndex = CallbackSingle_maxIndex + 1
			set this_2 = CallbackSingle_maxIndex
			set CallbackSingle_typeId[this_2] = 661
		else
			call error("Out of memory: Could not create CallbackSingle_nullTimer_Preloader.")
			set this_2 = 0
		endif
	else
		set CallbackSingle_firstFree = CallbackSingle_firstFree - 1
		set this_2 = CallbackSingle_nextFree[CallbackSingle_firstFree]
		set CallbackSingle_typeId[this_2] = 661
	endif
	return this_2
endfunction

function toRawCode takes integer value_2 returns string
	local string result_2 = ""
	local integer remainingValue = value_2
	local integer byteno = 0
	local integer charValue
	loop
		exitwhen byteno > 3
		set charValue = ModuloInteger(remainingValue, 256)
		set remainingValue = remainingValue / 256
		set result_2 = string_charAt(ObjectIds_CHARMAP, charValue) + result_2
		set byteno = byteno + 1
	endloop
	return result_2
endfunction

function int_toRawCode takes integer this_2 returns string
	return toRawCode(this_2)
endfunction

function init_Preloader takes nothing returns boolean
	local integer clVar
	set Preloader_autoFinish = true
	set Preloader_dumg = CreateGroup()
	set Preloader_dum = createUnit(Basics_DUMMY_PLAYER, DummyRecycler_DUMMY_UNIT_ID, 0., 0., 0.)
	if Preloader_dum == null then
		call error("DUMMY_UNITID (" + int_toRawCode(DummyRecycler_DUMMY_UNIT_ID) + ") not added correctly to the map.")
	endif
	if Preloader_autoFinish then
		set clVar = alloc_CallbackSingle_nullTimer_Preloader()
		call construct_CallbackSingle(clVar)
		call nullTimer(clVar)
	endif
	return true
endfunction

function init_Printing takes nothing returns boolean
	set Printing_DEBUG_LEVEL = 2
	set Printing_DEBUG_MSG_DURATION = 45.
	return true
endfunction

function init_Quaternion takes nothing returns boolean
	local real tuple_temp = 0.
	local real tuple_temp_2 = 0.
	local real tuple_temp_3 = 0.
	local real tuple_temp_4 = 1.
	local real tuple_temp_5 = 0.
	local real tuple_temp_6 = 0.
	local real tuple_temp_7 = 0.
	local real tuple_temp_8 = 0.
	return true
endfunction

function init_Raycast takes nothing returns boolean
	local boolean tuple_temp = false
	local real tuple_temp_2 = Vectors_ZERO2_x
	local real tuple_temp_3 = Vectors_ZERO2_y
	local real tuple_temp_4 = 0.
	local boolean tuple_temp_5 = false
	local real tuple_temp_6 = Vectors_ZERO3_x
	local real tuple_temp_7 = Vectors_ZERO3_y
	local real tuple_temp_8 = Vectors_ZERO3_z
	local real tuple_temp_9 = 0.
	local boolean tuple_temp_10 = false
	return true
endfunction

function init_Real takes nothing returns boolean
	set Real_REAL_MAX = 340282366920938000000000000000000000000.
	return true
endfunction

function init_RegisterEvents takes nothing returns boolean
	set RegisterEvents_onCastMap = new_HashMap()
	call registerPlayerUnitEvent(EVENT_PLAYER_UNIT_SPELL_EFFECT, ref_function_code__registerPlayerUnitEvent_RegisterEvents)
	return true
endfunction

function init_SafetyChecks takes nothing returns boolean
	set SafetyChecks_SAFETY_CHECKS_ENABLED = true
	return true
endfunction

function init_Simulate3dSound takes nothing returns boolean
	set Simulate3dSound_PERIOD = 0.2
	return true
endfunction

function init_SoundUtils takes nothing returns boolean
	set DynamicSound_TimedLoop_instanceCount = 0
	set DynamicSound_TimedLoop_triggerCond = null
	call Condition(ref_function_DynamicSound_TimedLoop_onExpire)
	return true
endfunction

function init_Sounds takes nothing returns boolean
	return true
endfunction

function init_Soundsets takes nothing returns boolean
	return true
endfunction

function init_StandardTextTags takes nothing returns boolean
	local real tuple_temp = 16.
	local real tuple_temp_2 = 0.
	return true
endfunction

function init_String takes nothing returns boolean
	set String_charset = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
	set String_numberset = "0123456789"
	call string_length(String_numberset)
	call string_length(String_charset)
	return true
endfunction

function init_StringBuffer takes nothing returns boolean
	set AbstractStringBuffer_MAX_BACKBUFFER_SIZE = 1024
	set AbstractStringBuffer_MAX_BUFFER_SIZE = 2048
	return true
endfunction

function alloc_ForForceCallback_execute_StringEncoder takes nothing returns integer
	local integer this_2
	if ForForceCallback_firstFree == 0 then
		if ForForceCallback_maxIndex < 32768 then
			set ForForceCallback_maxIndex = ForForceCallback_maxIndex + 1
			set this_2 = ForForceCallback_maxIndex
			set ForForceCallback_typeId[this_2] = 702
		else
			call error("Out of memory: Could not create ForForceCallback_execute_StringEncoder.")
			set this_2 = 0
		endif
	else
		set ForForceCallback_firstFree = ForForceCallback_firstFree - 1
		set this_2 = ForForceCallback_nextFree[ForForceCallback_firstFree]
		set ForForceCallback_typeId[this_2] = 702
	endif
	return this_2
endfunction

function alloc_ForForceCallback_execute_StringEncoder_492 takes nothing returns integer
	local integer this_2
	if ForForceCallback_firstFree == 0 then
		if ForForceCallback_maxIndex < 32768 then
			set ForForceCallback_maxIndex = ForForceCallback_maxIndex + 1
			set this_2 = ForForceCallback_maxIndex
			set ForForceCallback_typeId[this_2] = 703
		else
			call error("Out of memory: Could not create ForForceCallback_execute_StringEncoder.")
			set this_2 = 0
		endif
	else
		set ForForceCallback_firstFree = ForForceCallback_firstFree - 1
		set this_2 = ForForceCallback_nextFree[ForForceCallback_firstFree]
		set ForForceCallback_typeId[this_2] = 703
	endif
	return this_2
endfunction

function init_StringEncoder takes nothing returns boolean
	local integer clVar
	local integer clVar_2
	set StringEncoder_STRING_ALPHABET = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~"
	set StringEncoder_STRING_ALPHABET_LENGTH = 95
	set StringEncoder_alphabetLookup = new_HashMap()
	set clVar = alloc_ForForceCallback_execute_StringEncoder()
	call execute(clVar)
	set clVar_2 = alloc_ForForceCallback_execute_StringEncoder_492()
	call execute(clVar_2)
	return true
endfunction

function c2sInit takes nothing returns nothing
	set StringUtils_c2s[0] = ""
	set StringUtils_c2s[1] = ""
	set StringUtils_c2s[2] = ""
	set StringUtils_c2s[3] = ""
	set StringUtils_c2s[4] = ""
	set StringUtils_c2s[5] = ""
	set StringUtils_c2s[6] = ""
	set StringUtils_c2s[7] = ""
	set StringUtils_c2s[8] = ""
	set StringUtils_c2s[9] = "\t"
	set StringUtils_c2s[10] = "\n"
	set StringUtils_c2s[11] = ""
	set StringUtils_c2s[12] = ""
	set StringUtils_c2s[13] = "\r"
	set StringUtils_c2s[14] = ""
	set StringUtils_c2s[15] = ""
	set StringUtils_c2s[16] = ""
	set StringUtils_c2s[17] = ""
	set StringUtils_c2s[18] = ""
	set StringUtils_c2s[19] = ""
	set StringUtils_c2s[20] = ""
	set StringUtils_c2s[21] = ""
	set StringUtils_c2s[22] = ""
	set StringUtils_c2s[23] = ""
	set StringUtils_c2s[24] = ""
	set StringUtils_c2s[25] = ""
	set StringUtils_c2s[26] = ""
	set StringUtils_c2s[27] = ""
	set StringUtils_c2s[28] = ""
	set StringUtils_c2s[29] = ""
	set StringUtils_c2s[30] = ""
	set StringUtils_c2s[31] = ""
	set StringUtils_c2s[32] = " "
	set StringUtils_c2s[33] = "!"
	set StringUtils_c2s[34] = "\""
	set StringUtils_c2s[35] = "#"
	set StringUtils_c2s[36] = "$"
	set StringUtils_c2s[37] = "%"
	set StringUtils_c2s[38] = "&"
	set StringUtils_c2s[39] = "'"
	set StringUtils_c2s[40] = "("
	set StringUtils_c2s[41] = ")"
	set StringUtils_c2s[42] = "*"
	set StringUtils_c2s[43] = "+"
	set StringUtils_c2s[44] = ","
	set StringUtils_c2s[45] = "-"
	set StringUtils_c2s[46] = "."
	set StringUtils_c2s[47] = "/"
	set StringUtils_c2s[48] = "0"
	set StringUtils_c2s[49] = "1"
	set StringUtils_c2s[50] = "2"
	set StringUtils_c2s[51] = "3"
	set StringUtils_c2s[52] = "4"
	set StringUtils_c2s[53] = "5"
	set StringUtils_c2s[54] = "6"
	set StringUtils_c2s[55] = "7"
	set StringUtils_c2s[56] = "8"
	set StringUtils_c2s[57] = "9"
	set StringUtils_c2s[58] = ":"
	set StringUtils_c2s[59] = ";"
	set StringUtils_c2s[60] = "<"
	set StringUtils_c2s[61] = "="
	set StringUtils_c2s[62] = ">"
	set StringUtils_c2s[63] = "?"
	set StringUtils_c2s[64] = "@"
	set StringUtils_c2s[65] = "A"
	set StringUtils_c2s[66] = "B"
	set StringUtils_c2s[67] = "C"
	set StringUtils_c2s[68] = "D"
	set StringUtils_c2s[69] = "E"
	set StringUtils_c2s[70] = "F"
	set StringUtils_c2s[71] = "G"
	set StringUtils_c2s[72] = "H"
	set StringUtils_c2s[73] = "I"
	set StringUtils_c2s[74] = "J"
	set StringUtils_c2s[75] = "K"
	set StringUtils_c2s[76] = "L"
	set StringUtils_c2s[77] = "M"
	set StringUtils_c2s[78] = "N"
	set StringUtils_c2s[79] = "O"
	set StringUtils_c2s[80] = "P"
	set StringUtils_c2s[81] = "Q"
	set StringUtils_c2s[82] = "R"
	set StringUtils_c2s[83] = "S"
	set StringUtils_c2s[84] = "T"
	set StringUtils_c2s[85] = "U"
	set StringUtils_c2s[86] = "V"
	set StringUtils_c2s[87] = "W"
	set StringUtils_c2s[88] = "X"
	set StringUtils_c2s[89] = "Y"
	set StringUtils_c2s[90] = "Z"
	set StringUtils_c2s[91] = "["
	set StringUtils_c2s[92] = "\\"
	set StringUtils_c2s[93] = "]"
	set StringUtils_c2s[94] = "^"
	set StringUtils_c2s[95] = "_"
	set StringUtils_c2s[96] = "`"
	set StringUtils_c2s[97] = "a"
	set StringUtils_c2s[98] = "b"
	set StringUtils_c2s[99] = "c"
	set StringUtils_c2s[100] = "d"
	set StringUtils_c2s[101] = "e"
	set StringUtils_c2s[102] = "f"
	set StringUtils_c2s[103] = "g"
	set StringUtils_c2s[104] = "h"
	set StringUtils_c2s[105] = "i"
	set StringUtils_c2s[106] = "j"
	set StringUtils_c2s[107] = "k"
	set StringUtils_c2s[108] = "l"
	set StringUtils_c2s[109] = "m"
	set StringUtils_c2s[110] = "n"
	set StringUtils_c2s[111] = "o"
	set StringUtils_c2s[112] = "p"
	set StringUtils_c2s[113] = "q"
	set StringUtils_c2s[114] = "r"
	set StringUtils_c2s[115] = "s"
	set StringUtils_c2s[116] = "t"
	set StringUtils_c2s[117] = "u"
	set StringUtils_c2s[118] = "v"
	set StringUtils_c2s[119] = "w"
	set StringUtils_c2s[120] = "x"
	set StringUtils_c2s[121] = "y"
	set StringUtils_c2s[122] = "z"
	set StringUtils_c2s[123] = "{"
	set StringUtils_c2s[124] = "|"
	set StringUtils_c2s[125] = "}"
	set StringUtils_c2s[126] = "~"
	set StringUtils_c2s[127] = ""
endfunction

function s2cInit takes nothing returns nothing
	local integer i_2 = 0
	local integer temp = StringUtils_MAX_INDEX
	loop
		exitwhen i_2 > temp
		if string_toUpperCase(StringUtils_c2s[i_2]) == StringUtils_c2s[i_2] then
			call StringHash(StringUtils_c2s[i_2])
		endif
		set i_2 = i_2 + 1
	endloop
endfunction

function widthInit takes nothing returns nothing
endfunction

function initialize_1160 takes nothing returns nothing
	call c2sInit()
	call s2cInit()
	call widthInit()
endfunction

function stringToIndex takes string s returns integer
	local integer hash = string_getHash(s)
	loop
		exitwhen  not true
		if dispatch_Table_Table_Table_hasString(TypeCasting_typecastdata, hash) then
			if dispatch_Table_Table_Table_loadString(TypeCasting_typecastdata, hash) == s then
				exitwhen true
			endif
		else
			call dispatch_Table_Table_Table_saveString(TypeCasting_typecastdata, hash, s)
			exitwhen true
		endif
		set hash = hash + 1
	endloop
	return hash
endfunction

function init_StringUtils takes nothing returns boolean
	set StringUtils_MAX_INDEX = 126 + 1
	call initialize_1160()
	call stringToIndex("")
	return true
endfunction

function alloc_EventListener_add_SyncSimple takes nothing returns integer
	local integer this_2
	if EventListener_firstFree == 0 then
		if EventListener_maxIndex < 32768 then
			set EventListener_maxIndex = EventListener_maxIndex + 1
			set this_2 = EventListener_maxIndex
			set EventListener_typeId[this_2] = 680
		else
			call error("Out of memory: Could not create EventListener_add_SyncSimple.")
			set this_2 = 0
		endif
	else
		set EventListener_firstFree = EventListener_firstFree - 1
		set this_2 = EventListener_nextFree[EventListener_firstFree]
		set EventListener_typeId[this_2] = 680
	endif
	return this_2
endfunction

function alloc_EventListener_add_SyncSimple_483 takes nothing returns integer
	local integer this_2
	if EventListener_firstFree == 0 then
		if EventListener_maxIndex < 32768 then
			set EventListener_maxIndex = EventListener_maxIndex + 1
			set this_2 = EventListener_maxIndex
			set EventListener_typeId[this_2] = 681
		else
			call error("Out of memory: Could not create EventListener_add_SyncSimple.")
			set this_2 = 0
		endif
	else
		set EventListener_firstFree = EventListener_firstFree - 1
		set this_2 = EventListener_nextFree[EventListener_firstFree]
		set EventListener_typeId[this_2] = 681
	endif
	return this_2
endfunction

function alloc_BackIterator_471 takes nothing returns integer
	local integer this_2
	if BackIterator_firstFree_29 == 0 then
		if BackIterator_maxIndex_33 < 32768 then
			set BackIterator_maxIndex_33 = BackIterator_maxIndex_33 + 1
			set this_2 = BackIterator_maxIndex_33
		else
			call error("Out of memory: Could not create BackIterator.")
			set this_2 = 0
		endif
	else
		set BackIterator_firstFree_29 = BackIterator_firstFree_29 - 1
		set this_2 = BackIterator_nextFree_37[BackIterator_firstFree_29]
	endif
	return this_2
endfunction

function construct_BackIterator_631 takes integer this_2, boolean destroyOnClose returns nothing
endfunction

function new_BackIterator_1181 takes boolean destroyOnClose returns integer
	local integer this_2 = alloc_BackIterator_471()
	call construct_BackIterator_631(this_2, destroyOnClose)
	return this_2
endfunction

function onLeave takes code c returns nothing
	call trigger_addAction(OnUnitEnterLeave_eventTrigger, c)
endfunction

function init_SyncSimple takes nothing returns boolean
	local integer clVar
	local integer clVar_2
	local playerunitevent temp
	local playerevent temp_2
	local integer tuple_temp
	set SyncSimple_last = null
	set SyncSimple_count = 0
	set SimpleSynchronizer_LinkedListModule_first = 0
	set SimpleSynchronizer_LinkedListModule_last = 0
	set SimpleSynchronizer_LinkedListModule_size = 0
	call new_Iterator_1190(false)
	call new_BackIterator_1181(false)
	set tuple_temp = 268435455
	set SimpleSynchronizer_allPlayers_val = tuple_temp
	set temp = EVENT_PLAYER_UNIT_SELECTED
	set clVar = alloc_EventListener_add_SyncSimple()
	call construct_EventListener(clVar)
	call EventListener_add(temp, clVar)
	set temp_2 = EVENT_PLAYER_LEAVE
	set clVar_2 = alloc_EventListener_add_SyncSimple_483()
	call construct_EventListener(clVar_2)
	call EventListener_add(temp_2, clVar_2)
	call onLeave(ref_function_code__onLeave_SyncSimple)
	set temp = null
	set temp_2 = null
	return true
endfunction

function init_Table takes nothing returns boolean
	set Table_ht = InitHashtable()
	return true
endfunction

function init_TargetsAllowed takes nothing returns boolean
	return true
endfunction

function createItem_657 takes integer itemId, real pos_x, real pos_y, real pos_z returns item
	return CreateItem(itemId, pos_x, pos_y)
endfunction

function vec2_toVec3 takes real this_x, real this_y returns real
	set vec2_toVec3_return_x = this_x
	set vec2_toVec3_return_y = this_y
	set vec2_toVec3_return_z = 0.
	return vec2_toVec3_return_x
endfunction

function createItem takes integer itemId, real pos_x, real pos_y returns item
	return createItem_657(itemId, vec2_toVec3(pos_x, pos_y), vec2_toVec3_return_y, vec2_toVec3_return_z)
endfunction

function real_squared takes real this_2 returns real
	return this_2 * this_2
endfunction

function init_TerrainUtils takes nothing returns boolean
	local item receiver
	set TerrainUtils_MAX_RANGE_SQ = real_squared(10.)
	set TerrainUtils_DUMMY_ITEM_ID = 2003790951
	set receiver = createItem(TerrainUtils_DUMMY_ITEM_ID, Vectors_ZERO2_x, Vectors_ZERO2_y)
	call item_setVisible(receiver, false)
	set TerrainUtils_dummyItem = receiver
	set TerrainUtils_itemSearchRect = Rect(0., 0., 128., 128.)
	set TerrainUtils_hiddenItemsCount = 0
	set TerrainUtils_TILES_X = real_toInt(MapBounds_boundMax_x - MapBounds_boundMin_x) / 128 + 1
	set TerrainUtils_TILES_Y = real_toInt(MapBounds_boundMax_y - MapBounds_boundMin_y) / 128 + 1
	set receiver = null
	return true
endfunction

function init_Textures takes nothing returns boolean
	return true
endfunction

function init_Tiles takes nothing returns boolean
	return true
endfunction

function init_TimerUtils takes nothing returns boolean
	set TimerUtils_freeTimersCount = 0
	set TimerUtils_timerData = new_Table()
	set TimerUtils_HELD = 679645218
	set TimerUtils_timedLoopTimer = CreateTimer()
	set TimerUtils_timedLoopTrig = CreateTrigger()
	set TimerUtils_conditionCount = 0
	return true
endfunction

function initTypecastData takes nothing returns nothing
	call dispatch_Table_Table_Table_saveString(TypeCasting_typecastdata, 0, "")
endfunction

function init_TypeCasting takes nothing returns boolean
	set TypeCasting_typecastdata = new_Table()
	set TypeCasting_R2I_PRECISION = 1000.
	call initTypecastData()
	return true
endfunction

function init_UI takes nothing returns boolean
	return true
endfunction

function init_UnitIds takes nothing returns boolean
	return true
endfunction

function init_UnitIndexer takes nothing returns boolean
	set UnitIndexer_onIndexTrigger = CreateTrigger()
	set UnitIndexer_onDeindexTrigger = CreateTrigger()
	set UnitIndexer_tempUnitsCount = 0
	call onEnter(ref_function_code__onEnter_UnitIndexer)
	call onLeave(ref_function_code__onLeave_UnitIndexer)
	return true
endfunction

function init_Units takes nothing returns boolean
	return true
endfunction

function init_UpgradeObjEditing takes nothing returns boolean
	return true
endfunction

function init_Vectors takes nothing returns boolean
	local real tuple_temp = 0.
	local real tuple_temp_2 = 0.
	local real tuple_temp_3
	local real tuple_temp_4
	local real tuple_temp_5
	local real tuple_temp_6
	local real tuple_temp_7
	local real tuple_temp_8
	local real tuple_temp_9
	local real tuple_temp_10
	local real tuple_temp_11
	local real tuple_temp_12
	local real tuple_temp_13
	set Vectors_ZERO2_x = tuple_temp
	set Vectors_ZERO2_y = tuple_temp_2
	set tuple_temp_3 = 0.
	set tuple_temp_4 = 0.
	set tuple_temp_5 = 0.
	set Vectors_ZERO3_x = tuple_temp_3
	set Vectors_ZERO3_y = tuple_temp_4
	set Vectors_ZERO3_z = tuple_temp_5
	set tuple_temp_6 = 1.
	set tuple_temp_7 = 0.
	set tuple_temp_8 = 0.
	set tuple_temp_9 = 1.
	set tuple_temp_10 = -1.
	set tuple_temp_11 = 0.
	set tuple_temp_12 = 0.
	set tuple_temp_13 = -1.
	set Vectors_tempLoc = Location(0., 0.)
	return true
endfunction

function init_WeatherEffects takes nothing returns boolean
	return true
endfunction

function spellActions takes nothing returns nothing
	local integer index = unit_getIndex(GetTriggerUnit())
	if LastOrder_lastOrder[index] != 0 then
	endif
endfunction

function group_enumUnitsOfPlayer takes group this_2, player p, boolexpr filter returns nothing
	call GroupEnumUnitsOfPlayer(this_2, p, filter)
endfunction

function group_enumUnitsAll takes group this_2, boolexpr filter returns nothing
	local integer i_2 = 0
	local integer temp = bj_MAX_PLAYER_SLOTS - 1
	loop
		exitwhen i_2 > temp
		call group_enumUnitsOfPlayer(this_2, Player_players[i_2], filter)
		set i_2 = i_2 + 1
	endloop
endfunction

function trigger_destr takes trigger this_2 returns nothing
	call DestroyTrigger(this_2)
endfunction

function trigger_disable takes trigger this_2 returns nothing
	call DisableTrigger(this_2)
endfunction

function swap takes nothing returns nothing
	local boolean isEnabled = IsTriggerEnabled(DamageDetection_current)
	local integer i_2
	local integer temp
	call trigger_disable(DamageDetection_current)
	if DamageDetection_toDestroy != null then
		call trigger_destr(DamageDetection_toDestroy)
	endif
	set DamageDetection_toDestroy = DamageDetection_current
	set DamageDetection_current = CreateTrigger()
	if  not isEnabled then
		call trigger_disable(DamageDetection_current)
	endif
	call group_enumUnitsAll(Group_ENUM_GROUP, DamageDetection_filter)
	set i_2 = 0
	set temp = DamageDetection_funcNext - 1
	loop
		exitwhen i_2 > temp
		call trigger_addCondition(DamageDetection_current, DamageDetection_func[i_2])
		set i_2 = i_2 + 1
	endloop
endfunction

function initGlobals takes nothing returns nothing
	set LimitedExecuteCondition_firstFree = 0
	set LimitedExecuteCondition_maxIndex = 0
	set LimitedExecuteAction_firstFree = 0
	set LimitedExecuteAction_maxIndex = 0
	set Buffer_firstFree = 0
	set Buffer_maxIndex = 0
	set BufferSerializable_firstFree = 0
	set BufferSerializable_maxIndex = 0
	set OnCastListener_firstFree = 0
	set CallbackSingle_firstFree = 0
	set CallbackSingle_maxIndex = 0
	set EventListener_firstFree = 0
	set EventListener_maxIndex = 0
	set ForGroupCallback_firstFree = 0
	set ForGroupCallback_maxIndex = 0
	set CallbackCounted_firstFree = 0
	set DamageElement_firstFree = 0
	set DamageElement_maxIndex = 0
	set DamageInstance_firstFree = 0
	set DamageInstance_maxIndex = 0
	set DummyCaster_firstFree = 0
	set ArrayQueue_firstFree = 0
	set ArrayQueue_maxIndex = 0
	set DelayNode_firstFree = 0
	set ForForceCallback_firstFree = 0
	set ForForceCallback_maxIndex = 0
	set GamecacheBuffer_firstFree = 0
	set SynchronizerFunction_firstFree = 0
	set SynchronizerFunction_maxIndex = 0
	set HashList_firstFree = 0
	set Table_firstFree = 0
	set Table_maxIndex = 0
	set IOTaskExecutor_firstFree = 0
	set IOTask_firstFree = 0
	set IOTask_maxIndex = 0
	set BackIterator_firstFree = 0
	set BackIterator_maxIndex = 0
	set Iterator_firstFree = 0
	set Iterator_maxIndex = 0
	set Knockback3_firstFree = 0
	set BackIterator_firstFree_27 = 0
	set BackIterator_maxIndex_31 = 0
	set Iterator_firstFree_270 = 0
	set Iterator_maxIndex_274 = 0
	set Order_firstFree = 0
	set Order_maxIndex = 0
	set BackIterator_firstFree_28 = 0
	set BackIterator_maxIndex_32 = 0
	set Iterator_firstFree_271 = 0
	set Iterator_maxIndex_275 = 0
	set Comparator_firstFree = 0
	set Comparator_maxIndex = 0
	set LLBackIterator_firstFree = 0
	set LLEntry_firstFree = 0
	set LLEntry_maxIndex = 0
	set LLIterator_firstFree = 0
	set LLIterator_maxIndex = 0
	set LinkedList_firstFree = 0
	set LinkedList_maxIndex = 0
	set MetadataStore_firstFree = 0
	set AbstractFile_firstFree = 0
	set Network_firstFree = 0
	set NetworkFinishedCallback_firstFree = 0
	set NetworkFinishedCallback_maxIndex = 0
	set SynchronizationCallback_firstFree = 0
	set SynchronizationCallback_maxIndex = 0
	set IdGenerator_firstFree = 0
	set IdGenerator_maxIndex = 0
	set OrderStringFactory_firstFree = 0
	set OrderStringFactory_maxIndex = 0
	set Sim3DSound_firstFree = 0
	set DynamicSound_firstFree = 0
	set SoundInstance_firstFree = 0
	set ChunkElement_firstFree = 0
	set ChunkElement_maxIndex = 0
	set StringEncoder_firstFree = 0
	set SimpleSynchronizer_firstFree = 0
	set SimpleSynchronizer_maxIndex = 0
	set BackIterator_firstFree_29 = 0
	set BackIterator_maxIndex_33 = 0
	set Iterator_firstFree_272 = 0
	set Iterator_maxIndex_276 = 0
	set UnitIndex_firstFree = 0
	set UnitIndex_maxIndex = 0
	set ref_function_init_Abilities = function init_Abilities
	set ref_function_init_AbilityIds = function init_AbilityIds
	set ref_function_init_Real = function init_Real
	set ref_function_init_Integer = function init_Integer
	set ref_function_init_Angle = function init_Angle
	set ref_function_init_String = function init_String
	set ref_function_init_Vectors = function init_Vectors
	set ref_function_init_Player = function init_Player
	set ref_function_init_Basics = function init_Basics
	set ref_function_init_Maths = function init_Maths
	set ref_function_init_Printing = function init_Printing
	set ref_function_init_GameTimer = function init_GameTimer
	set ref_function_init_MagicFunctions = function init_MagicFunctions
	set ref_function_init_ErrorHandling = function init_ErrorHandling
	set ref_function_init_Matrices = function init_Matrices
	set ref_function_init_Quaternion = function init_Quaternion
	set ref_function_init_Table = function init_Table
	set ref_function_init_Playercolor = function init_Playercolor
	set ref_function_init_Colors = function init_Colors
	set ref_function_init_Group = function init_Group
	set ref_function_init_Lightning = function init_Lightning
	set ref_function_init_WeatherEffects = function init_WeatherEffects
	set ref_function_init_TypeCasting = function init_TypeCasting
	set ref_function_init_BigNum = function init_BigNum
	set ref_function_init_BitwiseInit = function init_BitwiseInit
	set ref_function_init_Buildings = function init_Buildings
	set ref_function_init_TargetsAllowed = function init_TargetsAllowed
	set ref_function_init_Doodads = function init_Doodads
	set ref_function_init_Icons = function init_Icons
	set ref_function_init_Objects = function init_Objects
	set ref_function_init_Sounds = function init_Sounds
	set ref_function_init_Soundsets = function init_Soundsets
	set ref_function_init_Textures = function init_Textures
	set ref_function_init_UI = function init_UI
	set ref_function_init_Units = function init_Units
	set ref_function_init_UnitIds = function init_UnitIds
	set ref_function_init_ClosureForGroups = function init_ClosureForGroups
	set ref_function_init_LinkedList = function init_LinkedList
	set ref_function_init_StringUtils = function init_StringUtils
	set ref_function_init_ObjectIds = function init_ObjectIds
	set ref_function_init_MapBounds = function init_MapBounds
	set ref_function_init_DummyRecycler = function init_DummyRecycler
	set ref_function_init_TimerUtils = function init_TimerUtils
	set ref_function_init_ClosureTimers = function init_ClosureTimers
	set ref_function_init_Preloader = function init_Preloader
	set ref_function_init_ObjectIdGenerator = function init_ObjectIdGenerator
	set ref_function_init_ChannelAbilityPreset = function init_ChannelAbilityPreset
	set ref_function_init_HashList = function init_HashList
	set ref_function_init_EventHelper = function init_EventHelper
	set ref_function_init_RegisterEvents = function init_RegisterEvents
	set ref_function_init_OnUnitEnterLeave = function init_OnUnitEnterLeave
	set ref_function_init_UnitIndexer = function init_UnitIndexer
	set ref_function_init_ClosureEvents = function init_ClosureEvents
	set ref_function_init_DamageDetection = function init_DamageDetection
	set ref_function_init_DamageEvent = function init_DamageEvent
	set ref_function_init_DebugFile = function init_DebugFile
	set ref_function_init_DialogBox = function init_DialogBox
	set ref_function_init_Execute = function init_Execute
	set ref_function_init_ForceTests = function init_ForceTests
	set ref_function_init_NetworkConfig = function init_NetworkConfig
	set ref_function_init_GamecacheKeys = function init_GamecacheKeys
	set ref_function_init_GroupUtils = function init_GroupUtils
	set ref_function_init_HashSet = function init_HashSet
	set ref_function_init_IOTaskExecutor = function init_IOTaskExecutor
	set ref_function_init_InstantDummyCaster = function init_InstantDummyCaster
	set ref_function_init_TerrainUtils = function init_TerrainUtils
	set ref_function_init_Knockback3 = function init_Knockback3
	set ref_function_init_LastOrder = function init_LastOrder
	set ref_function_init_LinkedListModuleTests = function init_LinkedListModuleTests
	set ref_function_init_LocalFiles = function init_LocalFiles
	set ref_function_init_Metadata = function init_Metadata
	set ref_function_init_SyncSimple = function init_SyncSimple
	set ref_function_init_SafetyChecks = function init_SafetyChecks
	set ref_function_init_StringBuffer = function init_StringBuffer
	set ref_function_init_OrderedStringBuffer = function init_OrderedStringBuffer
	set ref_function_init_StringEncoder = function init_StringEncoder
	set ref_function_init_Network = function init_Network
	set ref_function_init_Orders = function init_Orders
	set ref_function_init_PreloadIO = function init_PreloadIO
	set ref_function_init_Persistable = function init_Persistable
	set ref_function_init_Raycast = function init_Raycast
	set ref_function_init_Simulate3dSound = function init_Simulate3dSound
	set ref_function_init_SoundUtils = function init_SoundUtils
	set ref_function_init_StandardTextTags = function init_StandardTextTags
	set ref_function_init_Tiles = function init_Tiles
	set ref_function_init_UpgradeObjEditing = function init_UpgradeObjEditing
	set ref_function_code__onUnitIndex_ClosureEvents = function code__onUnitIndex_ClosureEvents
	set ref_function_code__onUnitDeindex_ClosureEvents = function code__onUnitDeindex_ClosureEvents
	set ref_function_EventListener_generalEventCallback = function EventListener_generalEventCallback
	set ref_function_code__Filter_ClosureForGroups = function code__Filter_ClosureForGroups
	set ref_function_code__Filter_DamageDetection = function code__Filter_DamageDetection
	set ref_function_code__onEnter_DamageDetection = function code__onEnter_DamageDetection
	set ref_function_swap = function swap
	set ref_function_code__onEnter_DamageEvent = function code__onEnter_DamageEvent
	set ref_function_DelayNode_recycle = function DelayNode_recycle
	set ref_function_executeCurrentCallback = function executeCurrentCallback
	set ref_function_code__startPeriodic_GameTimer = function code__startPeriodic_GameTimer
	set ref_function_TimedIOTaskExecutor_updateInstances = function TimedIOTaskExecutor_updateInstances
	set ref_function_code__EnumDestructablesInRect_Knockback3_Knockback3 = function code__EnumDestructablesInRect_Knockback3_Knockback3
	set ref_function_actions = function actions
	set ref_function_spellActions = function spellActions
	set ref_function_code__onUnitDeindex_LastOrder = function code__onUnitDeindex_LastOrder
	set ref_function_code__registerPlayerUnitEvent_RegisterEvents = function code__registerPlayerUnitEvent_RegisterEvents
	set ref_function_DynamicSound_TimedLoop_onExpire = function DynamicSound_TimedLoop_onExpire
	set ref_function_SoundDefinition_recycle = function SoundDefinition_recycle
	set ref_function_code__onLeave_SyncSimple = function code__onLeave_SyncSimple
	set ref_function_code__EnumItemsInRect_TerrainUtils = function code__EnumItemsInRect_TerrainUtils
	set ref_function_code__onEnter_UnitIndexer = function code__onEnter_UnitIndexer
	set ref_function_code__onLeave_UnitIndexer = function code__onLeave_UnitIndexer
	set ref_function_code__addAction_nullTimer_ClosureEvents = function code__addAction_nullTimer_ClosureEvents
	set ref_function_code__addAction_nullTimer_ClosureEvents_679 = function code__addAction_nullTimer_ClosureEvents_607
	set ref_function_code__addAction_nullTimer_ClosureEvents_680 = function code__addAction_nullTimer_ClosureEvents_608
	set ref_function_code__registerPlayerUnitEvent_nullTimer_ClosureEvents = function code__registerPlayerUnitEvent_nullTimer_ClosureEvents
	set ref_function_code__start_CallbackSingle_ClosureTimers = function code__start_CallbackSingle_ClosureTimers
	set ref_function_code__Filter_registerEnterRegion_nullTimer_OnUnitEnterLeave = function code__Filter_registerEnterRegion_nullTimer_OnUnitEnterLeave
	set ref_function_code__registerPlayerUnitEvent_nullTimer_OnUnitEnterLeave = function code__registerPlayerUnitEvent_nullTimer_OnUnitEnterLeave
	set ref_function_code__ForGroup_nullTimer_OnUnitEnterLeave = function code__ForGroup_nullTimer_OnUnitEnterLeave
endfunction

function main takes nothing returns nothing
	local trigger initTrig
	call initGlobals()
	set initTrig = CreateTrigger()
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Abilities))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Abilities.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_AbilityIds))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package AbilityIds.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Real))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Real.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Integer))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Integer.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Angle))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Angle.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_String))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package String.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Vectors))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Vectors.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Player))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Player.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Basics))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Basics.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Maths))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Maths.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Printing))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Printing.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_GameTimer))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package GameTimer.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_MagicFunctions))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package MagicFunctions.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_ErrorHandling))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package ErrorHandling.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Matrices))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Matrices.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Quaternion))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Quaternion.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Table))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Table.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Playercolor))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Playercolor.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Colors))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Colors.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Group))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Group.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Lightning))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Lightning.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_WeatherEffects))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package WeatherEffects.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_TypeCasting))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package TypeCasting.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_BigNum))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package BigNum.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_BitwiseInit))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package BitwiseInit.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Buildings))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Buildings.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_TargetsAllowed))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package TargetsAllowed.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Doodads))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Doodads.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Icons))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Icons.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Objects))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Objects.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Sounds))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Sounds.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Soundsets))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Soundsets.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Textures))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Textures.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_UI))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package UI.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Units))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Units.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_UnitIds))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package UnitIds.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_ClosureForGroups))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package ClosureForGroups.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_LinkedList))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package LinkedList.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_StringUtils))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package StringUtils.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_ObjectIds))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package ObjectIds.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_MapBounds))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package MapBounds.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_DummyRecycler))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package DummyRecycler.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_TimerUtils))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package TimerUtils.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_ClosureTimers))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package ClosureTimers.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Preloader))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Preloader.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_ObjectIdGenerator))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package ObjectIdGenerator.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_ChannelAbilityPreset))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package ChannelAbilityPreset.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_HashList))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package HashList.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_EventHelper))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package EventHelper.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_RegisterEvents))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package RegisterEvents.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_OnUnitEnterLeave))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package OnUnitEnterLeave.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_UnitIndexer))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package UnitIndexer.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_ClosureEvents))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package ClosureEvents.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_DamageDetection))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package DamageDetection.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_DamageEvent))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package DamageEvent.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_DebugFile))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package DebugFile.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_DialogBox))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package DialogBox.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Execute))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Execute.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_ForceTests))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package ForceTests.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_NetworkConfig))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package NetworkConfig.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_GamecacheKeys))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package GamecacheKeys.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_GroupUtils))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package GroupUtils.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_HashSet))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package HashSet.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_IOTaskExecutor))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package IOTaskExecutor.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_InstantDummyCaster))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package InstantDummyCaster.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_TerrainUtils))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package TerrainUtils.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Knockback3))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Knockback3.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_LastOrder))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package LastOrder.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_LinkedListModuleTests))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package LinkedListModuleTests.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_LocalFiles))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package LocalFiles.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Metadata))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Metadata.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_SyncSimple))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package SyncSimple.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_SafetyChecks))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package SafetyChecks.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_StringBuffer))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package StringBuffer.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_OrderedStringBuffer))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package OrderedStringBuffer.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_StringEncoder))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package StringEncoder.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Network))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Network.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Orders))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Orders.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_PreloadIO))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package PreloadIO.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Persistable))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Persistable.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Raycast))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Raycast.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Simulate3dSound))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Simulate3dSound.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_SoundUtils))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package SoundUtils.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_StandardTextTags))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package StandardTextTags.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_Tiles))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package Tiles.")
	endif
	call TriggerClearConditions(initTrig)
	call TriggerAddCondition(initTrig, Condition(ref_function_init_UpgradeObjEditing))
	if  not TriggerEvaluate(initTrig) then
		call DisplayTimedTextToPlayer(GetLocalPlayer(), 0., 0., 45., "Could not initialize package UpgradeObjEditing.")
	endif
	call TriggerClearConditions(initTrig)
	call DestroyTrigger(initTrig)
	set initTrig = null
endfunction

function config takes nothing returns nothing
endfunction

