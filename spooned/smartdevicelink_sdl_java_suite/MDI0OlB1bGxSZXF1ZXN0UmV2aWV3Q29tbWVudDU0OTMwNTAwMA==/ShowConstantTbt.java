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
[CtImportImpl]import java.util.Hashtable;
[CtImportImpl]import java.util.List;
[CtUnresolvedImport]import com.smartdevicelink.protocol.enums.FunctionID;
[CtUnresolvedImport]import com.smartdevicelink.proxy.RPCRequest;
[CtClassImpl][CtJavaDocImpl]/**
 * <p>This RPC is used to update the user with navigation information for the constantly shown screen (base screen), but
 * also for the alert type screen</p>
 *
 * <p>Function Group: Navigation</p>
 *
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 *
 * @see AlertManeuver
 * @see UpdateTurnList
 * @since SmartDeviceLink 2.0
 */
public class ShowConstantTBT extends [CtTypeReferenceImpl]com.smartdevicelink.proxy.RPCRequest {
    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_TEXT1 = [CtLiteralImpl]"navigationText1";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_TEXT2 = [CtLiteralImpl]"navigationText2";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_ETA = [CtLiteralImpl]"eta";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_TOTAL_DISTANCE = [CtLiteralImpl]"totalDistance";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_DISTANCE_TO_MANEUVER = [CtLiteralImpl]"distanceToManeuver";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_DISTANCE_TO_MANEUVER_SCALE = [CtLiteralImpl]"distanceToManeuverScale";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_MANEUVER_IMAGE = [CtLiteralImpl]"turnIcon";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_NEXT_MANEUVER_IMAGE = [CtLiteralImpl]"nextTurnIcon";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_MANEUVER_COMPLETE = [CtLiteralImpl]"maneuverComplete";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_SOFT_BUTTONS = [CtLiteralImpl]"softButtons";

    [CtFieldImpl]public static final [CtTypeReferenceImpl]java.lang.String KEY_TIME_TO_DESTINATION = [CtLiteralImpl]"timeToDestination";

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Constructs a new ShowConstantTBT object
     */
    public ShowConstantTBT() [CtBlockImpl]{
        [CtInvocationImpl]super([CtInvocationImpl][CtTypeAccessImpl]FunctionID.SHOW_CONSTANT_TBT.toString());
    }

    [CtConstructorImpl][CtJavaDocImpl]/**
     * Constructs a new ShowConstantTBT object indicated by the Hashtable parameter
     *
     * @param hash
     * 		The Hashtable to use
     */
    public ShowConstantTBT([CtParameterImpl][CtTypeReferenceImpl]java.util.Hashtable<[CtTypeReferenceImpl]java.lang.String, [CtTypeReferenceImpl]java.lang.Object> hash) [CtBlockImpl]{
        [CtInvocationImpl]super([CtVariableReadImpl]hash);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets a text for navigation text field 1
     *
     * @param navigationText1
     * 		a String value representing a text for navigation text field 1
     * 		<p></p>
     * 		<b>Notes: </b>Maxlength=500
     */
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT setNavigationText1([CtParameterImpl][CtTypeReferenceImpl]java.lang.String navigationText1) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_TEXT1, [CtVariableReadImpl]navigationText1);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a text for navigation text field 1
     *
     * @return String -a String value representing a text for navigation text field 1
     */
    public [CtTypeReferenceImpl]java.lang.String getNavigationText1() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getString([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_TEXT1);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets a text for navigation text field 2
     *
     * @param navigationText2
     * 		a String value representing a text for navigation text field 2
     * 		<p></p>
     * 		<b>Notes: </b>Maxlength=500
     */
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT setNavigationText2([CtParameterImpl][CtTypeReferenceImpl]java.lang.String navigationText2) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_TEXT2, [CtVariableReadImpl]navigationText2);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a text for navigation text field 2
     *
     * @return String -a String value representing a text for navigation text field 2
     */
    public [CtTypeReferenceImpl]java.lang.String getNavigationText2() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getString([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_TEXT2);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets a text field for estimated time of arrival
     *
     * @param eta
     * 		a String value representing a text field for estimated time of arrival
     * 		<p></p>
     * 		<b>Notes: </b>Maxlength=500
     */
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT setEta([CtParameterImpl][CtTypeReferenceImpl]java.lang.String eta) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_ETA, [CtVariableReadImpl]eta);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a text field for estimated time of arrival
     *
     * @return String -a String value representing a text field for estimated time of arrival
     */
    public [CtTypeReferenceImpl]java.lang.String getEta() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getString([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_ETA);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets a text field for total distance
     *
     * @param totalDistance
     * 		a String value representing a text field for total distance
     * 		<p></p>
     * 		<b>Notes: </b>Maxlength=500
     */
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT setTotalDistance([CtParameterImpl][CtTypeReferenceImpl]java.lang.String totalDistance) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_TOTAL_DISTANCE, [CtVariableReadImpl]totalDistance);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a text field for total distance
     *
     * @return String -a String value representing a text field for total distance
     */
    public [CtTypeReferenceImpl]java.lang.String getTotalDistance() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getString([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_TOTAL_DISTANCE);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets an Image for turn icon
     *
     * @param turnIcon
     * 		an Image value
     */
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT setTurnIcon([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.Image turnIcon) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_MANEUVER_IMAGE, [CtVariableReadImpl]turnIcon);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets an Image for turn icon
     *
     * @return Image -an Image value representing an Image for turnicon
     */
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.Image getTurnIcon() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.Image) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.Image.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_MANEUVER_IMAGE)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets an Image for nextTurnIcon
     *
     * @param nextTurnIcon
     * 		an Image value
     */
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT setNextTurnIcon([CtParameterImpl][CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.Image nextTurnIcon) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_NEXT_MANEUVER_IMAGE, [CtVariableReadImpl]nextTurnIcon);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets an Image for nextTurnIcon
     *
     * @return Image -an Image value representing an Image for nextTurnIcon
     */
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.Image getNextTurnIcon() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.Image) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.Image.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_NEXT_MANEUVER_IMAGE)));
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the distanceToManeuver.
     *
     * @param distanceToManeuver
     * 		Distance (in meters) until next maneuver. May be used to calculate progress bar.
     * 		{"num_min_value": 0.0, "num_max_value": 1000000000.0}
     * @since SmartDeviceLink 2.0.0
     */
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT setDistanceToManeuver([CtParameterImpl][CtTypeReferenceImpl]java.lang.Double distanceToManeuver) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_DISTANCE_TO_MANEUVER, [CtVariableReadImpl]distanceToManeuver);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the distanceToManeuver.
     *
     * @return Float Distance (in meters) until next maneuver. May be used to calculate progress bar.
    {"num_min_value": 0.0, "num_max_value": 1000000000.0}
     * @since SmartDeviceLink 2.0.0
     */
    public [CtTypeReferenceImpl]java.lang.Double getDistanceToManeuver() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object object = [CtInvocationImpl]getParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_DISTANCE_TO_MANEUVER);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.smartdevicelink.util.SdlDataTypeConverter.objectToDouble([CtVariableReadImpl]object);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Sets the distanceToManeuverScale.
     *
     * @param distanceToManeuverScale
     * 		Distance (in meters) from previous maneuver to next maneuver. May be used to calculate
     * 		progress bar.
     * 		{"num_min_value": 0.0, "num_max_value": 1000000000.0}
     * @since SmartDeviceLink 2.0.0
     */
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT setDistanceToManeuverScale([CtParameterImpl][CtTypeReferenceImpl]java.lang.Double distanceToManeuverScale) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_DISTANCE_TO_MANEUVER_SCALE, [CtVariableReadImpl]distanceToManeuverScale);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets the distanceToManeuverScale.
     *
     * @return Float Distance (in meters) from previous maneuver to next maneuver. May be used to calculate
    progress bar.
    {"num_min_value": 0.0, "num_max_value": 1000000000.0}
     * @since SmartDeviceLink 2.0.0
     */
    public [CtTypeReferenceImpl]java.lang.Double getDistanceToManeuverScale() [CtBlockImpl]{
        [CtLocalVariableImpl][CtTypeReferenceImpl]java.lang.Object object = [CtInvocationImpl]getParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_DISTANCE_TO_MANEUVER_SCALE);
        [CtReturnImpl]return [CtInvocationImpl][CtTypeAccessImpl]com.smartdevicelink.util.SdlDataTypeConverter.objectToDouble([CtVariableReadImpl]object);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * <p>Sets a maneuver complete flag. If and when a maneuver has completed while an AlertManeuver is active, the app
     * must send this value set to TRUE in order to clear the AlertManeuver overlay
     * If omitted the value will be assumed as FALSE</p>
     *
     * @param maneuverComplete
     * 		a Boolean value
     */
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT setManeuverComplete([CtParameterImpl][CtTypeReferenceImpl]java.lang.Boolean maneuverComplete) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_MANEUVER_COMPLETE, [CtVariableReadImpl]maneuverComplete);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets a maneuver complete flag
     *
     * @return Boolean -a Boolean value
     */
    public [CtTypeReferenceImpl]java.lang.Boolean getManeuverComplete() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getBoolean([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_MANEUVER_COMPLETE);
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * <p>Sets Three dynamic SoftButtons available (first SoftButton is fixed to "Turns"). If omitted on supported
     * displays, the currently displayed SoftButton values will not change</p>
     *
     * <p><b>Notes: </b>Minsize=0; Maxsize=3</p>
     *
     * @param softButtons
     * 		a List<SoftButton> value
     */
    public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT setSoftButtons([CtParameterImpl][CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.SoftButton> softButtons) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_SOFT_BUTTONS, [CtVariableReadImpl]softButtons);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl][CtJavaDocImpl]/**
     * Gets Three dynamic SoftButtons available (first SoftButton is fixed to "Turns"). If omitted on supported
     * displays, the currently displayed SoftButton values will not change
     *
     * @return Vector<SoftButton> -a Vector<SoftButton> value
     */
    [CtAnnotationImpl]@java.lang.SuppressWarnings([CtLiteralImpl]"unchecked")
    public [CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.SoftButton> getSoftButtons() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl](([CtTypeReferenceImpl]java.util.List<[CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.SoftButton>) (getObject([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.SoftButton.class, [CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_SOFT_BUTTONS)));
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT setTimeToDestination([CtParameterImpl][CtTypeReferenceImpl]java.lang.String timeToDestination) [CtBlockImpl]{
        [CtInvocationImpl]setParameters([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_TIME_TO_DESTINATION, [CtVariableReadImpl]timeToDestination);
        [CtReturnImpl]return [CtThisAccessImpl]this;
    }

    [CtMethodImpl]public [CtTypeReferenceImpl]java.lang.String getTimeToDestination() [CtBlockImpl]{
        [CtReturnImpl]return [CtInvocationImpl]getString([CtFieldReadImpl]com.smartdevicelink.proxy.rpc.ShowConstantTBT.KEY_TIME_TO_DESTINATION);
    }
}