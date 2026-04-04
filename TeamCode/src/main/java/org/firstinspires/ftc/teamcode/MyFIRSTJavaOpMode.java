package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Basic Drive", group = "Test")
public class MyFIRSTJavaOpMode extends LinearOpMode {
    private DcMotor frontLeft, frontRight;
    private DcMotor backLeft, backRight;
    private DcMotor intakeMotor;
    private DcMotor launcherRight, launcherLeft;


 @Override
    public void runOpMode() {            //Waits till Driver presses Initialize
        telemetry.addData("Status", "Initialized");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        //intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        //launcherRight = hardwareMap.get(DcMotor.class, "launcherRight");
        //launcherLeft = hardwareMap.get(DcMotor.class, "launcherLeft");

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Front Left Motor: ", frontLeft.getPower());
        telemetry.addData("Front Right Motor: ", frontRight.getPower());
        telemetry.addData("Back Left Motor: ", backLeft.getPower());
        telemetry.addData("Back Right Motor: ", backRight.getPower());
        telemetry.update();


        waitForStart(); //Waits until Drivers presses Start

        while (opModeIsActive()) {
            telemetry.addData("Status","OpMode Running");
            telemetry.addData("Front Left Motor: ", frontLeft.getPower());
            telemetry.addData("Front Right Motor: ", frontRight.getPower());
            telemetry.addData("Back Left Motor: ", backLeft.getPower());
            telemetry.addData("Back Right Motor: ", backRight.getPower());
            telemetry.update();


            double lStickY = gamepad1.left_stick_y; //Methods for DriveTrain Motors
            double lStickX = gamepad1.left_stick_x;
            double rStickX = gamepad1.right_stick_x;
            double rStickY = gamepad1.right_stick_y;


            frontLeft.setPower(lStickY + lStickX - rStickX); //Calculations for DriveTrain Motors
            backLeft.setPower(lStickY - lStickX - rStickX);
            frontRight.setPower(-lStickY - lStickX - rStickX);
            backRight.setPower(-lStickY + lStickX - rStickX);

            boolean lBumper = gamepad1.left_bumper; // Bumpers are true or false values
            boolean rBumper = gamepad1.right_bumper;


            double lTrigger = gamepad1.left_trigger; //Triggers are decimal values
            double rTrigger = gamepad1.right_trigger;

            boolean aButton = gamepad1.a; // X on DualSense
            boolean bButton = gamepad1.b; // Circle on DualSense
            boolean xButton = gamepad1.x; // Square on DualSense
            boolean yButton = gamepad1.y; // Triangle on DualSense

        }
}
    }
