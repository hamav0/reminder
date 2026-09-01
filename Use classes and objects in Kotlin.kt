import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// can be a parent, or is a super class
// it is REQUERED TO USE val in the (val name: String, val category: String)
open class SmartDevice(val name: String, val category: String) {

    var deviceStatus = "online"
    // this makes that anyone can read status, but only sub-classes and this class can change it
    /*protected set(value) {
           field = value
       }*/
    // but because there's no (if) condition you can actualy save some place
    protected set

    // you can also override non changable values
    open val deviceType = "unknown"
    
    fun printDeviceInfo() {
        println("Device name: $name, category: $category, type: $deviceType")
    }
    
    // can be over-riden
    open fun turnOn() {
        deviceStatus = "on"
    }

    open fun turnOff() {
        deviceStatus = "off"
    }
}
// Smart TV IS-A smart device.
class SmartTvDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {
        
        override val deviceType = "Smart TV"

        /*
        private var speakerVolume = 2
        set(value) {
            if (value in 0..100) {
                field = value
            }
        }*/
        
        private var speakerVolume by RangeRegulator(initialValue = 2, minValue = 0, maxValue = 100)

        private var channelNumber by RangeRegulator(initialValue = 1, minValue = 0, maxValue = 200)

        
        fun increaseSpeakerVolume() {
        speakerVolume++
        println("Speaker volume increased to $speakerVolume.")
    } 
        fun decreaseSpeakerVolume() {
        speakerVolume--
        println("Speaker volume decreased to $speakerVolume.")
    } 
         fun previousChannel() {
        channelNumber--
        println("Channel number decreased to $channelNumber.")
    }
        fun nextChannel() {
        channelNumber++
        println("Channel number increased to $channelNumber.")
    }

        // super. is for using the functions that is in the parent (super) class
        override fun turnOn() {
        super.turnOn()
        println(
            "$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                "set to $channelNumber."
        )
    }
        override fun turnOff() {
        super.turnOff()
        println("$name turned off")
    }
}
    
class SmartLightDevice(deviceName: String, deviceCategory: String) :
    SmartDevice(name = deviceName, category = deviceCategory) {
        
        override val deviceType = "Smart Light"
        
        private var brightnessLevel by RangeRegulator(initialValue = 0, minValue = 0, maxValue = 100)
        
        fun increaseBrightness() {
        brightnessLevel++
        println("Brightness increased to $brightnessLevel.")
    }
        fun decreaseBrightness() {
        brightnessLevel--
        println("Brightness decreased to $brightnessLevel.")
    }
        // over-riding
        override fun turnOn() {
        super.turnOn()
        brightnessLevel = 2
        println("$name turned on. The brightness level is $brightnessLevel.")
    }
        override fun turnOff() {
        super.turnOff()
        brightnessLevel = 0
        println("Smart Light turned off")
    }
}
    // The SmartHome class HAS-A smart TV device.
class SmartHome(
    val smartTvDevice: SmartTvDevice,
    val smartLightDevice: SmartLightDevice
) {
	var deviceTurnOnCount by RangeRegulator(initialValue = 0, minValue = 0, maxValue = 2)
    
   fun turnOnTv() {
       if(smartTvDevice.deviceStatus == "off") {
       deviceTurnOnCount++
       smartTvDevice.turnOn()
       }
    }

    fun turnOffTv() {
         if(smartTvDevice.deviceStatus == "on") {
        deviceTurnOnCount--
        smartTvDevice.turnOff()
         }
    }

    fun increaseTvVolume() {
         if(smartTvDevice.deviceStatus == "on") {
        smartTvDevice.increaseSpeakerVolume() }
    }
    fun decreaseTvVolume() {
         if(smartTvDevice.deviceStatus == "on") {
        smartTvDevice.decreaseSpeakerVolume() }
    }

    fun changeTvChannelToNext() {
        if(smartTvDevice.deviceStatus == "on") {
        smartTvDevice.nextChannel() }
    }
    fun changeTvChannelToPrevious() {
        if(smartTvDevice.deviceStatus == "on") {
        smartTvDevice.previousChannel() }
    }
    
    fun turnOnLight() {
        if(smartLightDevice.deviceStatus == "off") {
        deviceTurnOnCount++
        smartLightDevice.turnOn() }
    }

    fun turnOffLight() {
        if(smartLightDevice.deviceStatus == "on") {
        deviceTurnOnCount--
        smartLightDevice.turnOff() }
    }
    
    fun increaseLightBrightness() {
        if(smartLightDevice.deviceStatus == "on") {
        smartLightDevice.increaseBrightness() }
    }
    fun decreaseLightBrightness() {
        if(smartLightDevice.deviceStatus == "on") {
        smartLightDevice.decreaseBrightness() }
    }
    
    fun turnOffAllDevices() {
        turnOffTv()
        turnOffLight()
    }
    fun printSmartTvInfo() { smartTvDevice.printDeviceInfo() }
    fun printSmartLightInfo() { smartLightDevice.printDeviceInfo() }
}
// here i've made an RangeRegulator, so you can assign this rule to a values, instead of making one for everyone
class RangeRegulator(
    initialValue: Int,
    private val minValue: Int,
    private val maxValue: Int
) : ReadWriteProperty<Any?, Int> {

    var fieldData = initialValue

    //These methods act as the properties' getter and setter functions.
    override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
        return fieldData
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
        if (value in minValue..maxValue) {
            fieldData = value
        }
    }
}
fun main() {
    /*
    var smartDevice: SmartDevice = SmartTvDevice("Android TV", "Entertainment")
    smartDevice.turnOn()
    smartDevice.printDeviceInfo()
    
    smartDevice = SmartLightDevice("Google Light", "Utility")
    smartDevice.turnOn()
    smartDevice.printDeviceInfo()
    */
    
    var HOME: SmartHome = SmartHome(SmartTvDevice("Android TV", "Entertainment"), SmartLightDevice("Google Light", "Utility"))
    HOME.turnOnTv()
    HOME.turnOnLight()
    HOME.increaseLightBrightness()
    HOME.decreaseTvVolume()
    HOME.changeTvChannelToPrevious()
    HOME.turnOffAllDevices()
    
    HOME.decreaseLightBrightness()
    HOME.increaseTvVolume()
    HOME.changeTvChannelToNext()
    HOME.printSmartTvInfo()
    HOME.printSmartLightInfo()
    
}
