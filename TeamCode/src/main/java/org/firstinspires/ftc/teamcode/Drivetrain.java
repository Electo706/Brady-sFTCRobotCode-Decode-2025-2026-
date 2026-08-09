package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Drivetrain {
    private DcMotor frontLeft, frontRight, backLeft, backRight;



    public void init(HardwareMap HardwareMap) {
        frontLeft = HardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = HardwareMap.get(DcMotor.class, "frontRight");
        backLeft = HardwareMap.get(DcMotor.class, "backLeft");
        backRight = HardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    public void power(float lStickY, float lStickX, float rStickX) {
        frontLeft.setPower(lStickY + lStickX - rStickX); // -1.0 - 1.0 power level (sent from Joysticks)
        backLeft.setPower(-lStickX - rStickX - rStickX);
        frontRight.setPower(lStickX - rStickX - rStickX);
        backRight.setPower(-lStickX + rStickX - rStickX);
    }
    public void telem(Telemetry telemetry) {
        telemetry.addData("FrontLeftMotorPower: ", frontLeft.getPower());
        telemetry.addData("FrontRightMotorPower: ", frontRight.getPower());
        telemetry.addData("BackLeftMotorPower: ", backLeft.getPower());
        telemetry.addData("BackRightMotorPower: ", backRight.getPower());
        telemetry.update();
    }
}
