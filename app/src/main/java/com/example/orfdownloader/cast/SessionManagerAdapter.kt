package com.example.orfdownloader.cast

import com.google.android.gms.cast.framework.Session
import com.google.android.gms.cast.framework.SessionManagerListener

interface SessionManagerAdapter :  SessionManagerListener<Session>{
    override fun onSessionStarting(p0: Session?) = Unit

    override fun onSessionStarted(p0: Session?, p1: String?) = Unit

    override fun onSessionStartFailed(p0: Session?, p1: Int) = Unit

    override fun onSessionEnding(p0: Session?) = Unit

    override fun onSessionEnded(p0: Session?, p1: Int) = Unit

    override fun onSessionResuming(p0: Session?, p1: String?) = Unit

    override fun onSessionResumed(p0: Session?, p1: Boolean) = Unit

    override fun onSessionResumeFailed(p0: Session?, p1: Int) = Unit

    override fun onSessionSuspended(p0: Session?, p1: Int) = Unit
}