package com.example.administrator.vidioplayer.event

data class IsPlayEvent(val isPlay: Boolean)

data class SeekEvent(val isNext:Boolean)

data class IconCanSeeEvent(val isCanSee:Boolean)

data class OrientationChangeEvent(val isSensor:Boolean)

class HideActionBarEvent();