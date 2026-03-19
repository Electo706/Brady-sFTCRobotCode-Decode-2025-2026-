package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Basic Drive", group = "Test")
public class MyFIRSTJavaOpMode extends LinearOpMode {
    private DcMotor frontLeft, frontRight;
    private DcMotor backLeft, backRight;


 @Override
    public void runOpMode() {            //Initialization
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        System.out.println("Front Left Motor: " + frontLeft);
        System.out.println("Front Right Motor: " + frontRight);
        System.out.println("Back Left Motor: " + backLeft);
        System.out.println("Back Right Motor: " + backRight);


        waitForStart();

        while (opModeIsActive()) {


            double lStickY = gamepad1.left_stick_y;
            double lStickX = gamepad1.left_stick_x;
            double rStickX = gamepad1.right_stick_x;
            double rStickY = gamepad1.right_stick_y;


            frontLeft.setPower(lStickY + lStickX - rStickX);
            backLeft.setPower(lStickY - lStickX - rStickX);
            frontRight.setPower(-lStickY - lStickX - rStickX);
            backRight.setPower(-lStickY + lStickX - rStickX);

            System.out.println("Running Teleop");


        }
}
    }
