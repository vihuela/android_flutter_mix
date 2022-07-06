package com.hoo.android

import android.content.Context
import android.widget.Toast
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant


object FlutterInject {
    private const val ENGINE_ID = "LoginModule_engine_id"
    private const val CHANNEL_NATIVE = "com.flutter/method_channel"

    fun getIntent(context: Context) = FlutterActivity.withCachedEngine(ENGINE_ID).build(context)

    fun inject(context: Context) {
        val flutterEngine = FlutterEngine(context.applicationContext)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        FlutterEngineCache.getInstance().put(ENGINE_ID, flutterEngine)
        configureFlutterEngine(context, flutterEngine)
    }

    private fun configureFlutterEngine(context: Context, flutterEngine: FlutterEngine) {
        GeneratedPluginRegistrant.registerWith(flutterEngine)
        val mc = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL_NATIVE)
        mc.setMethodCallHandler { call, result ->
            when (call.method) {
                "loginWechat" -> {
                    Toast.makeText(context, call.arguments.toString(), Toast.LENGTH_SHORT).show()
                    mc.invokeMethod("loginWechat", "来自native")
                }
                "loginApple" -> {

                }
                "loginWeibo" -> {

                }
                "loginFacebook" -> {

                }
                else -> {
                    result.notImplemented()
                }
            }
        }
    }
}