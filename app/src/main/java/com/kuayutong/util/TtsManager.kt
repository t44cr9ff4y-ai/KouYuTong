package com.kuayutong.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.Locale

/**
 * TTS Manager for American English pronunciation
 * Singleton pattern, initialized once per app session
 */
object TtsManager {
    
    private const val TAG = "TtsManager"
    
    private var textToSpeech: TextToSpeech? = null
    private var isInitialized = false
    private var pendingText: String? = null
    private var pendingCallback: (() -> Unit)? = null
    
    /**
     * Initialize TTS with American English
     * Call this from Application.onCreate() or first Activity
     */
    fun init(context: Context, onInitialized: (() -> Unit)? = null) {
        if (isInitialized) {
            onInitialized?.invoke()
            return
        }
        
        textToSpeech = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = textToSpeech?.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e(TAG, "American English not supported, falling back to default")
                    textToSpeech?.language = Locale.ENGLISH
                }
                isInitialized = true
                Log.d(TAG, "TTS initialized successfully")
                onInitialized?.invoke()
                
                // Speak pending text if any
                pendingText?.let { text ->
                    speak(text, pendingCallback)
                    pendingText = null
                    pendingCallback = null
                }
            } else {
                Log.e(TAG, "TTS initialization failed")
            }
        }
    }
    
    /**
     * Speak text with American pronunciation
     * @param text The text to speak
     * @param onDone Callback when speech is done
     */
    fun speak(text: String, onDone: (() -> Unit)? = null) {
        if (!isInitialized) {
            // Queue for later
            pendingText = text
            pendingCallback = onDone
            return
        }
        
        textToSpeech?.stop()
        textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TTS_${System.currentTimeMillis()}")
        
        // Note: onDone callback is not reliably called with QUEUE_FLUSH
        // For reliable callback, use UtteranceProgressListener with QUEUE_ADD
        if (onDone != null) {
            textToSpeech?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) {
                    onDone()
                }
                override fun onError(utteranceId: String?) {}
            })
            textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TTS_${System.currentTimeMillis()}")
        }
    }
    
    /**
     * Stop speaking
     */
    fun stop() {
        textToSpeech?.stop()
    }
    
    /**
     * Release TTS resources (call in Application.onTerminate or Activity.onDestroy)
     */
    fun shutdown() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
        isInitialized = false
    }
    
    /**
     * Check if TTS is ready
     */
    fun isReady(): Boolean = isInitialized
}
