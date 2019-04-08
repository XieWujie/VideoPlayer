package com.example.administrator.videoplayer.event

data class IsPlayEvent(val isPlay: Boolean)

data class SeekEvent(val isNext:Boolean)

data class IconCanSeeEvent(val isCanSee:Boolean)

data class OrientationChangeEvent(var orientation:Int)

class HideActionBarEvent();

data class OrientationEvent(val isLand:Boolean)