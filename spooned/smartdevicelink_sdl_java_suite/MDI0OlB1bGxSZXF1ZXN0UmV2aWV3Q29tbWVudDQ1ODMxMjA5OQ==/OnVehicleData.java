[CompilationUnitImpl][CtCommentImpl]/* Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following
disclaimer in the documentation and/or other materials provided with the
distribution.

Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
contributors may be used to endorse or promote products derived from this 
software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
 */
[CtPackageDeclarationImpl]package com.smartdevicelink.proxy.rpc;
[CtUnresolvedImport]import com.smartdevicelink.util.SdlDataTypeConverter;
[CtUnresolvedImport]import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
[CtUnresolvedImport]import com.smartdevicelink.proxy.rpc.enums.TurnSignal;
[CtUnresolvedImport]import com.smartdevicelink.proxy.rpc.enums.PRNDL;
[CtImportImpl]import java.util.Hashtable;
[CtUnresolvedImport]import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
[CtUnresolvedImport]import com.smartdevicelink.proxy.RPCNotification;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.smartdevicelink.proxy.rpc.enums.WiperStatus;
[CtUnresolvedImport]import com.smartdevicelink.protocol.enums.FunctionID;
[CtUnresolvedImport]import com.smartdevicelink.proxy.rpc.enums.ElectronicParkBrakeStatus;
[CtClassImpl][CtJavaDocImpl]/**
 * Individual requested DID result and data.
 *
 *
 * <p>Callback for the periodic and non periodic vehicle data read function.</p>
 *
 * <p> <b>Note:</b></p>
 *
 * Initially SDL sends SubscribeVehicleData for getting the periodic updates from HMI whenever each of subscribed data types changes. OnVehicleData is expected to bring such updated values to SDL
 *
 *
 *
 *
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>Gps</td>
 * 			<td>Boolean</td>
 * 			<td>GPS data. See {@linkplain com.smartdevicelink.proxy.rpc.GPSData} for details</td>
 *                 <td>N</td>
 * 			<td>Subscribable </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>Speed</td>
 * 			<td>Float</td>
 * 			<td>The vehicle speed in kilometers per hour</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rpm</td>
 * 			<td>Integer</td>
 * 			<td>The number of revolutions per minute of the engine</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>fuelLevel</td>
 * 			<td>Float</td>
 * 			<td>The fuel level in the tank (percentage)</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>fuelLevel_State</td>
 * 			<td>ComponentVolumeStatus</td>
 * 			<td>The fuel level state (Ok/Low)</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>instantFuelConsumption</td>
 * 			<td>Float</td>
 * 			<td>The instantaneous fuel consumption in microlitres</td>
 *                 <td>N</td>
 * 			<td>Subscribable </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>externalTemperature</td>
 * 			<td>Float</td>
 * 			<td>The external temperature in degrees celsius.</td>
 *                 <td>N</td>
 * 			<td>Subscribable </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>vin</td>
 * 			<td>String</td>
 * 			<td>Vehicle identification number.</td>
 *                 <td>N</td>
 * 			<td>Subscribable </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>prndl</td>
 * 			<td>PRNDL</td>
 * 			<td>Currently selected gear.</td>
 *                 <td>N</td>
 * 			<td>Subscribable </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>tirePressure</td>
 * 			<td>TireStatus</td>
 * 			<td>Tire pressure status</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>odometer</td>
 * 			<td>Integer</td>
 * 			<td>Odometer in km</td>
 *                 <td>N</td>
 * 			<td>Subscribable </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>beltStatus</td>
 * 			<td>BeltStatus</td>
 * 			<td>The status of the seat belts.</td>
 *                 <td>N</td>
 * 			<td>Subscribable </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>bodyInformation</td>
 * 			<td>BodyInformation</td>
 * 			<td>The body information including power modes.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>deviceStatus</td>
 * 			<td>DeviceStatus</td>
 * 			<td>The connected mobile device status including signal and battery strength.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>eCallInfo</td>
 * 			<td>ECallInfo</td>
 * 			<td>Emergency Call notification and confirmation data.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>airbagStatus</td>
 * 			<td>AirBagStatus</td>
 * 			<td>The status of the air bags.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>emergencyEvent</td>
 * 			<td>EmergencyEvernt</td>
 * 			<td>Information related to an emergency event (and if it occurred).</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>clusterModeStatus</td>
 * 			<td>ClusterModeStatus</td>
 * 			<td>The status modes of the instrument panel cluster.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>myKey</td>
 * 			<td>MyKey</td>
 * 			<td>Information related to the MyKey feature.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 * 		<tr>
 * 			<td>driverBraking</td>
 * 			<td>vehicleDataEventStatus</td>
 * 			<td>The status of the brake pedal.</td>
 *                 <td>N</td>
 * 			<td>Subscribable</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>wiperStatus</td>
 * 			<td>WiperStatus</td>
 * 			<td>The status of the wipers</td>
 *                 <td>N</td>
 * 			<td> </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>headLampStatus</td>
 * 			<td>headLampStatus</td>
 * 			<td>Status of the head lamps</td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>engineTorque</td>
 * 			<td>Float</td>
 * 			<td>Torque value for engine (in Nm) on non-diesel variants</td>
 *                 <td>N</td>
 * 			<td>minvalue:-1000; maxvalue:2000</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>engineOilLife</td>
 * 			<td>Float</td>
 * 			<td>The estimated percentage of remaining oil life of the engine</td>
 *                 <td>N</td>
 * 			<td>minvalue:0; maxvalue:100</td>
 * 			<td>SmartDeviceLink 5.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>accPedalPosition</td>
 * 			<td>Float</td>
 * 			<td>Accelerator pedal position (percentage depressed)</td>
 *                 <td>N</td>
 * 			<td>minvalue: 0; maxvalue:100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>steeringWheelAngle</td>
 * 			<td>Float</td>
 * 			<td>Current angle of the steering wheel (in deg)</td>
 *                 <td>N</td>
 * 			<td> minvalue: -2000; maxvalue:2000</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 	     <tr>
 * 			<td>cloudAppVehicleID</td>
 * 			<td>String</td>
 * 			<td>ID for the vehicle when connecting to cloud applications</td>
 * 				<td>N</td>
 * 				<td></td>
 * 			<td>SmartDeviceLink 5.1 </td>
 * 		</tr>
 *  </table>
 *
 * @since SmartDeviceLink 1.0
 * @see SubscribeVehicleData
 * @see UnsubscribeVehicleData
 */
public class OnVehicleData extends [CtTypeReferenceImpl]com.smartdevicelink.proxy.RPCNotification {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_SPEED = [CtLiteralImpl]"speed";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_RPM = [CtLiteralImpl]"rpm";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_EXTERNAL_TEMPERATURE = [CtLiteralImpl]"externalTemperature";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_FUEL_LEVEL = [CtLiteralImpl]"fuelLevel";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_VIN = [CtLiteralImpl]"vin";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_PRNDL = [CtLiteralImpl]"prndl";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_TIRE_PRESSURE = [CtLiteralImpl]"tirePressure";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_ENGINE_TORQUE = [CtLiteralImpl]"engineTorque";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_ENGINE_OIL_LIFE = [CtLiteralImpl]"engineOilLife";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_ODOMETER = [CtLiteralImpl]"odometer";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_GPS = [CtLiteralImpl]"gps";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_FUEL_LEVEL_STATE = [CtLiteralImpl]"fuelLevel_State";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_INSTANT_FUEL_CONSUMPTION = [CtLiteralImpl]"instantFuelConsumption";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_BELT_STATUS = [CtLiteralImpl]"beltStatus";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_BODY_INFORMATION = [CtLiteralImpl]"bodyInformation";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_DEVICE_STATUS = [CtLiteralImpl]"deviceStatus";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_DRIVER_BRAKING = [CtLiteralImpl]"driverBraking";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_WIPER_STATUS = [CtLiteralImpl]"wiperStatus";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_HEAD_LAMP_STATUS = [CtLiteralImpl]"headLampStatus";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_ACC_PEDAL_POSITION = [CtLiteralImpl]"accPedalPosition";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_STEERING_WHEEL_ANGLE = [CtLiteralImpl]"steeringWheelAngle";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_E_CALL_INFO = [CtLiteralImpl]"eCallInfo";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_AIRBAG_STATUS = [CtLiteralImpl]"airbagStatus";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_EMERGENCY_EVENT = [CtLiteralImpl]"emergencyEvent";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_CLUSTER_MODE_STATUS = [CtLiteralImpl]"clusterModeStatus";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_MY_KEY = [CtLiteralImpl]"myKey";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_FUEL_RANGE = [CtLiteralImpl]"fuelRange";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_TURN_SIGNAL = [CtLiteralImpl]"turnSignal";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_ELECTRONIC_PARK_BRAKE_STATUS = [CtLiteralImpl]"electronicParkBrakeStatus";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_CLOUD_APP_VEHICLE_ID = [CtLiteralImpl]"cloudAppVehicleID";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_STABILITY_CONTROLS_STATUS = [CtLiteralImpl]"stabilityControlsStatus";

    [CtConstructorImpl]public OnVehicleData() [CtBlockImpl]{
        [CtInvocationImpl]super([CtInvocationImpl][CtTypeAccessImpl]FunctionID.ON_VEHICLE_DATA.toString());
    }

    [CtConstructorImpl]public OnVehicleData([CtParameterImpl][CtTypeReferenceImpl]java.util.Hashtable<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> hash) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]hash);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setGps([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.GPSData gps) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_GPS, [CtVariableReadImpl]gps);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.GPSData getGps() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.GPSData) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.GPSData.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_GPS)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setSpeed([CtParameterImpl][CtTypeReferenceImpl]java.lang.Double speed) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_SPEED, [CtVariableReadImpl]speed);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Double getSpeed() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object object = [CtInvocationImpl]getParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_SPEED);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.smartdevicelink.util.SdlDataTypeConverter.objectToDouble([CtVariableReadImpl]object);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setRpm([CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer rpm) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_RPM, [CtVariableReadImpl]rpm);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Integer getRpm() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getInteger([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_RPM);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setFuelLevel([CtParameterImpl][CtTypeReferenceImpl]java.lang.Double fuelLevel) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_FUEL_LEVEL, [CtVariableReadImpl]fuelLevel);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Double getFuelLevel() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object object = [CtInvocationImpl]getParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_FUEL_LEVEL);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.smartdevicelink.util.SdlDataTypeConverter.objectToDouble([CtVariableReadImpl]object);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]void setFuelLevel_State([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus fuelLevel_State) [CtBlockImpl]{
        [CtInvocationImpl]setFuelLevelState([CtVariableReadImpl]fuelLevel_State);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.Deprecated
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus getFuelLevel_State() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getFuelLevelState();
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setFuelLevelState([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus fuelLevelState) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_FUEL_LEVEL_STATE, [CtVariableReadImpl]fuelLevelState);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus getFuelLevelState() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_FUEL_LEVEL_STATE)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setInstantFuelConsumption([CtParameterImpl][CtTypeReferenceImpl]java.lang.Double instantFuelConsumption) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_INSTANT_FUEL_CONSUMPTION, [CtVariableReadImpl]instantFuelConsumption);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Double getInstantFuelConsumption() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object object = [CtInvocationImpl]getParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_INSTANT_FUEL_CONSUMPTION);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.smartdevicelink.util.SdlDataTypeConverter.objectToDouble([CtVariableReadImpl]object);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setExternalTemperature([CtParameterImpl][CtTypeReferenceImpl]java.lang.Double externalTemperature) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_EXTERNAL_TEMPERATURE, [CtVariableReadImpl]externalTemperature);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Double getExternalTemperature() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object object = [CtInvocationImpl]getParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_EXTERNAL_TEMPERATURE);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.smartdevicelink.util.SdlDataTypeConverter.objectToDouble([CtVariableReadImpl]object);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setVin([CtParameterImpl][CtTypeReferenceImpl]java.lang.String vin) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_VIN, [CtVariableReadImpl]vin);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getVin() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getString([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_VIN);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setPrndl([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.PRNDL prndl) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_PRNDL, [CtVariableReadImpl]prndl);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.PRNDL getPrndl() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.PRNDL) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.enums.PRNDL.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_PRNDL)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setTirePressure([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.TireStatus tirePressure) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_TIRE_PRESSURE, [CtVariableReadImpl]tirePressure);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.TireStatus getTirePressure() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.TireStatus) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.TireStatus.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_TIRE_PRESSURE)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setOdometer([CtParameterImpl][CtTypeReferenceImpl]java.lang.Integer odometer) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_ODOMETER, [CtVariableReadImpl]odometer);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Integer getOdometer() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getInteger([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_ODOMETER);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setBeltStatus([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.BeltStatus beltStatus) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_BELT_STATUS, [CtVariableReadImpl]beltStatus);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.BeltStatus getBeltStatus() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.BeltStatus) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.BeltStatus.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_BELT_STATUS)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setBodyInformation([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.BodyInformation bodyInformation) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_BODY_INFORMATION, [CtVariableReadImpl]bodyInformation);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.BodyInformation getBodyInformation() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.BodyInformation) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.BodyInformation.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_BODY_INFORMATION)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setDeviceStatus([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.DeviceStatus deviceStatus) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_DEVICE_STATUS, [CtVariableReadImpl]deviceStatus);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.DeviceStatus getDeviceStatus() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.DeviceStatus) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.DeviceStatus.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_DEVICE_STATUS)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setDriverBraking([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus driverBraking) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_DRIVER_BRAKING, [CtVariableReadImpl]driverBraking);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus getDriverBraking() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_DRIVER_BRAKING)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setWiperStatus([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.WiperStatus wiperStatus) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_WIPER_STATUS, [CtVariableReadImpl]wiperStatus);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.WiperStatus getWiperStatus() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.WiperStatus) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.enums.WiperStatus.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_WIPER_STATUS)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setHeadLampStatus([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.HeadLampStatus headLampStatus) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_HEAD_LAMP_STATUS, [CtVariableReadImpl]headLampStatus);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.HeadLampStatus getHeadLampStatus() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.HeadLampStatus) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.HeadLampStatus.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_HEAD_LAMP_STATUS)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setEngineTorque([CtParameterImpl][CtTypeReferenceImpl]java.lang.Double engineTorque) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_ENGINE_TORQUE, [CtVariableReadImpl]engineTorque);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Double getEngineTorque() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object object = [CtInvocationImpl]getParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_ENGINE_TORQUE);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.smartdevicelink.util.SdlDataTypeConverter.objectToDouble([CtVariableReadImpl]object);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setEngineOilLife([CtParameterImpl][CtTypeReferenceImpl]java.lang.Float engineOilLife) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_ENGINE_OIL_LIFE, [CtVariableReadImpl]engineOilLife);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Float getEngineOilLife() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object object = [CtInvocationImpl]getParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_ENGINE_OIL_LIFE);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.smartdevicelink.util.SdlDataTypeConverter.objectToFloat([CtVariableReadImpl]object);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setAccPedalPosition([CtParameterImpl][CtTypeReferenceImpl]java.lang.Double accPedalPosition) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_ACC_PEDAL_POSITION, [CtVariableReadImpl]accPedalPosition);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Double getAccPedalPosition() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object object = [CtInvocationImpl]getParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_ACC_PEDAL_POSITION);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.smartdevicelink.util.SdlDataTypeConverter.objectToDouble([CtVariableReadImpl]object);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setSteeringWheelAngle([CtParameterImpl][CtTypeReferenceImpl]java.lang.Double steeringWheelAngle) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_STEERING_WHEEL_ANGLE, [CtVariableReadImpl]steeringWheelAngle);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.Double getSteeringWheelAngle() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object object = [CtInvocationImpl]getParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_STEERING_WHEEL_ANGLE);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.smartdevicelink.util.SdlDataTypeConverter.objectToDouble([CtVariableReadImpl]object);
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setECallInfo([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ECallInfo eCallInfo) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_E_CALL_INFO, [CtVariableReadImpl]eCallInfo);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ECallInfo getECallInfo() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ECallInfo) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ECallInfo.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_E_CALL_INFO)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setAirbagStatus([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.AirbagStatus airbagStatus) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_AIRBAG_STATUS, [CtVariableReadImpl]airbagStatus);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.AirbagStatus getAirbagStatus() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.AirbagStatus) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.AirbagStatus.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_AIRBAG_STATUS)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setEmergencyEvent([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.EmergencyEvent emergencyEvent) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_EMERGENCY_EVENT, [CtVariableReadImpl]emergencyEvent);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.EmergencyEvent getEmergencyEvent() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.EmergencyEvent) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.EmergencyEvent.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_EMERGENCY_EVENT)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setClusterModeStatus([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ClusterModeStatus clusterModeStatus) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_CLUSTER_MODE_STATUS, [CtVariableReadImpl]clusterModeStatus);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ClusterModeStatus getClusterModeStatus() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ClusterModeStatus) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ClusterModeStatus.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_CLUSTER_MODE_STATUS)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]void setMyKey([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.MyKey myKey) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_MY_KEY, [CtVariableReadImpl]myKey);
    }

    [CtMethodImpl][CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.MyKey getMyKey() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.MyKey) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.MyKey.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_MY_KEY)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets Fuel Range List. Fuel Range - The estimate range in KM the vehicle can travel based on fuel level and consumption.
     *
     * @param fuelRange
     */
    public [CtTypeReferenceImpl]void setFuelRange([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.FuelRange> fuelRange) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_FUEL_RANGE, [CtVariableReadImpl]fuelRange);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets Fuel Range List.
     *
     * @return List<FuelRange>
    Fuel Range - The estimate range in KM the vehicle can travel based on fuel level and consumption.
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.FuelRange> getFuelRange() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.FuelRange>) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.FuelRange.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_FUEL_RANGE)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets turnSignal
     *
     * @param turnSignal
     */
    public [CtTypeReferenceImpl]void setTurnSignal([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.TurnSignal turnSignal) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_TURN_SIGNAL, [CtVariableReadImpl]turnSignal);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets turnSignal
     *
     * @return TurnSignal
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.TurnSignal getTurnSignal() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.TurnSignal) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.enums.TurnSignal.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_TURN_SIGNAL)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets electronicParkBrakeStatus
     *
     * @param electronicParkBrakeStatus
     */
    public [CtTypeReferenceImpl]void setElectronicParkBrakeStatus([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.ElectronicParkBrakeStatus electronicParkBrakeStatus) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_ELECTRONIC_PARK_BRAKE_STATUS, [CtVariableReadImpl]electronicParkBrakeStatus);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets electronicParkBrakeStatus
     *
     * @return ElectronicParkBrakeStatus
     */
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.ElectronicParkBrakeStatus getElectronicParkBrakeStatus() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.enums.ElectronicParkBrakeStatus) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.enums.ElectronicParkBrakeStatus.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_ELECTRONIC_PARK_BRAKE_STATUS)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets a string value for the cloud app vehicle ID
     *
     * @param cloudAppVehicleID
     * 		a string value
     */
    public [CtTypeReferenceImpl]void setCloudAppVehicleID([CtParameterImpl][CtTypeReferenceImpl]java.lang.String cloudAppVehicleID) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_CLOUD_APP_VEHICLE_ID, [CtVariableReadImpl]cloudAppVehicleID);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a String value of the returned cloud app vehicle ID
     *
     * @return a String value.
     */
    public [CtTypeReferenceImpl]java.lang.String getCloudAppVehicleID() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getString([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_CLOUD_APP_VEHICLE_ID);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets a value for OEM Custom VehicleData.
     *
     * @param vehicleDataName
     * 		a String value
     * @param vehicleDataState
     * 		a VehicleDataResult value
     */
    public [CtTypeReferenceImpl]void setOEMCustomVehicleData([CtParameterImpl][CtTypeReferenceImpl]java.lang.String vehicleDataName, [CtParameterImpl][CtTypeReferenceImpl]java.lang.Object vehicleDataState) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtVariableReadImpl]vehicleDataName, [CtVariableReadImpl]vehicleDataState);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a VehicleData value for the vehicle data item.
     *
     * @return a Object related to the vehicle data
     */
    public [CtTypeReferenceImpl]java.lang.Object getOEMCustomVehicleData([CtParameterImpl][CtTypeReferenceImpl]java.lang.String vehicleDataName) [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getParameters([CtVariableReadImpl]vehicleDataName);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets a value for StabilityControlsStatus.
     *
     * @param stabilityControlsStatus
     * 		a StabilityControlsStatus value
     */
    public [CtTypeReferenceImpl]void setStabilityControlsStatus([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.StabilityControlsStatus stabilityControlsStatus) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_STABILITY_CONTROLS_STATUS, [CtVariableReadImpl]stabilityControlsStatus);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a StabilityControlsStatus value.
     *
     * @return a StabilityControlsStatus
     */
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.StabilityControlsStatus getStabilityControlsStatus() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.StabilityControlsStatus) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.StabilityControlsStatus.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.OnVehicleData.KEY_STABILITY_CONTROLS_STATUS)));
    }
}