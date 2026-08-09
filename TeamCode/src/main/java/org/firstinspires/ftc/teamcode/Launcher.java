package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Launcher {
    private DcMotorEx launcherRight;
    private DcMotorEx launcherLeft;

    public void init(HardwareMap hardwareMap) {

        launcherRight = hardwareMap.get(DcMotorEx.class, "launcherRight");
        launcherLeft = hardwareMap.get(DcMotorEx.class, "launcherLeft");
        launcherLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        launcherRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        launcherRight.setDirection(DcMotorEx.Direction.FORWARD);
        launcherLeft.setDirection(DcMotorEx.Direction.FORWARD);
    }
    int[] launcherSpeedSizes = {0, 280, 560, 840, 1120, 1400, 1680, 1960, 2240, 2520, 2800,};
    /*   0 = Launcher at   0% Velocity/Speed (0000 RPM)
         280 = Launcher at  10% Velocity/Speed (0600 RPM)
         560 = Launcher at  20% Velocity/Speed (1200 RPM)
         840 = Launcher at  30% Velocity/Speed (1800 RPM)
        1120 = Launcher at  40% Velocity/Speed (2400 RPM)
        1400 = Launcher at  50% Velocity/Speed (3000 RPM)
        1680 = Launcher at  60% Velocity/Speed (3600 RPM)
        1960 = Launcher at  70% Velocity/Speed (4200 RPM)
        2240 = Launcher at  80% Velocity/Speed (4800 RPM)
        2520 = Launcher at  90% Velocity/Speed (5400 RPM)
        2800 = Launcher at 100% Velocity/Speed (6000 RPM)  */
    int speedSetting = 0;
    int curSelLaunchVelo = launcherSpeedSizes[speedSetting];
    double launcherRightRPM = (launcherRight.getVelocity() / 28 * 60);
    double launcherLeftRPM = (launcherLeft.getVelocity() / 28 * 60);

    public void run(double rightTriggerInput,double leftTriggerInput,
                                 boolean aButtonPressed, boolean bButtonPressed) {
        if (aButtonPressed && speedSetting < launcherSpeedSizes.length -1) {
            speedSetting++;
        }
        if (bButtonPressed && speedSetting > 0) {
            speedSetting--;
        }
        launcherLeft.setVelocity((rightTriggerInput - leftTriggerInput) * launcherSpeedSizes[speedSetting]);
        launcherRight.setVelocity((rightTriggerInput - leftTriggerInput) * launcherSpeedSizes[speedSetting]);
    }
    public void telem(Telemetry telemetry) {
        telemetry.addData("LauncherRightPower: ", launcherRight.getPower());
        telemetry.addData("LauncherLeftPower: ", launcherLeft.getPower());
        telemetry.addData("LauncherRightRPM: ", launcherRightRPM);
        telemetry.addData("LauncherLeftRPM: ", launcherLeftRPM);
        telemetry.addData("LauncherVelocityMode: ", speedSetting);
        telemetry.addData("LauncherTargetVelocity: ", curSelLaunchVelo);
        telemetry.update();
    }
    public void LED(boolean enabled, Gamepad gamepad1, Gamepad gamepad2) {
        if (launcherLeftRPM >= curSelLaunchVelo) {
            gamepad1.setLedColor(0, 255, 0, Gamepad.LED_DURATION_CONTINUOUS);
            gamepad2.setLedColor(0,255,0, Gamepad.LED_DURATION_CONTINUOUS);
        } else {
            // Optional: Turn the LED off or change color if it's NOT ready
            gamepad1.setLedColor(0, 0, 255, Gamepad.LED_DURATION_CONTINUOUS);
            gamepad2.setLedColor(255,0,0, Gamepad. LED_DURATION_CONTINUOUS);
        }

    }






}
