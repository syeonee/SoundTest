package com.syeon.soundtest.audio.core;

public interface Callback {
    void onBufferAvailable(byte[] buffer);
}
