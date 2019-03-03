package com.phicdy.totoanticipation

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import com.phicdy.totoanticipation.model.notification.DeadlineNotification

import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    @Throws(Exception::class)
    fun useAppContext() {
        DeadlineNotification.show(InstrumentationRegistry.getTargetContext())
    }
}
