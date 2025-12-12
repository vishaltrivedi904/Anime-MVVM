# Anime Application ProGuard Rules
# Production-ready obfuscation and optimization rules

#---------------------------------
# General Android Rules
#---------------------------------
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keepattributes InnerClasses,EnclosingMethod

# Keep application class
-keep class com.example.animeapplication.AnimeApplication { *; }

#---------------------------------
# Kotlin
#---------------------------------
-dontwarn kotlin.**
-keep class kotlin.Metadata { *; }
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

#---------------------------------
# Retrofit & OkHttp
#---------------------------------
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-keep class retrofit2.** { *; }
-keepattributes RuntimeVisibleAnnotations,RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# Keep API service interfaces
-keep,allowobfuscation interface com.example.animeapplication.data.remote.AnimeApiService

#---------------------------------
# Gson
#---------------------------------
-keepattributes Signature
-keep class com.google.gson.** { *; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Keep DTO classes with their field names for Gson
-keep class com.example.animeapplication.data.dto.** { *; }
-keepclassmembers class com.example.animeapplication.data.dto.** {
    <fields>;
}

#---------------------------------
# Room Database
#---------------------------------
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**
-keep class com.example.animeapplication.data.local.** { *; }
-keepclassmembers class com.example.animeapplication.data.local.entity.** {
    <fields>;
}

#---------------------------------
# Hilt / Dagger
#---------------------------------
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ComponentSupplier { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }
-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel

#---------------------------------
# Jetpack Compose
#---------------------------------
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

#---------------------------------
# Coroutines
#---------------------------------
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

#---------------------------------
# Security - Obfuscate but keep functionality
#---------------------------------
# Keep security manager but obfuscate internals
-keep class com.example.animeapplication.security.SecurityManager {
    public <methods>;
}

# Obfuscate security constants maximally
-assumenosideeffects class com.example.animeapplication.security.SecureConstants {
    private static final java.lang.String[] ROOT_PATHS;
    private static final java.lang.String[] EMULATOR_INDICATORS;
}

#---------------------------------
# Remove Logging in Release
#---------------------------------
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
    public static int wtf(...);
}

-assumenosideeffects class timber.log.Timber* {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
    public static *** wtf(...);
}

#---------------------------------
# Paging
#---------------------------------
-keep class androidx.paging.** { *; }
-dontwarn androidx.paging.**

#---------------------------------
# Media3 / ExoPlayer
#---------------------------------
-keep class androidx.media3.** { *; }
-dontwarn androidx.media3.**

#---------------------------------
# Coil
#---------------------------------
-dontwarn coil.**
-keep class coil.** { *; }

#---------------------------------
# Domain Models (keep for serialization if needed)
#---------------------------------
-keep class com.example.animeapplication.domain.model.** { *; }

#---------------------------------
# Optimization settings
#---------------------------------
-optimizationpasses 5
-dontusemixedcaseclassnames
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*