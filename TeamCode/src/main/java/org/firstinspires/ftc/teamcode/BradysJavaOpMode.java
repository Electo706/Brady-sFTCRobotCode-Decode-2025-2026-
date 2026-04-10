package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;


@TeleOp(name = "Basic Drive", group = "Test")
public class BradysJavaOpMode extends LinearOpMode {
    private DcMotor frontLeft, frontRight;
    private DcMotor backLeft, backRight;
    private DcMotorEx intakeMotor;
    private DcMotorEx launcherRight, launcherLeft;


    @Override
    public void runOpMode() {            //Waits till Driver presses Initialize

        //HardwareMap.get Finds Motors and Sensors configured by Driver Hub
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        launcherRight = hardwareMap.get(DcMotorEx.class, "launcherRight");
        launcherLeft = hardwareMap.get(DcMotorEx.class, "launcherLeft");

        //Intake and Launcher RunMode
        intakeMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        launcherLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        launcherRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        //Chassis Motor Directions DO NOT CHANGE ANY OF THESE OR I WILL TOUCH YOU
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        //See below
        double[] launcherSpeedSizes = {0, 280, 560, 840, 1120, 1400, 1680, 1960, 2240, 2520, 2800,};
        int stepIndex = 0;
        /*
        The code above this comment uses StepSizes to make "Launcher Modes" to help shoot more
        consistently and at the exact rpm or velocity you want.

           0 = Launcher at   0% Velocity/Speed (0 RPM AKA NOT RUNNING)
         280 = Launcher at  10% Velocity/Speed (600 RPM)
         560 = Launcher at  20% Velocity/Speed (1200 RPM)
         840 = Launcher at  30% Velocity/Speed (1800 RPM)
        1120 = Launcher at  40% Velocity/Speed (2400 RPM)
        1400 = Launcher at  50% Velocity/Speed (3000 RPM)
        1680 = Launcher at  60% Velocity/Speed (3600 RPM)
        1960 = Launcher at  70% Velocity/Speed (4200 RPM)
        2240 = Launcher at  80% Velocity/Speed (4800 RPM)
        2520 = Launcher at  90% Velocity/Speed (5400 RPM)
        2800 = Launcher at 100% Velocity/Speed (6000 RPM AKA MAXED SPEED)


        This code was inspired by code used in the PIDFTuning.java file made with the tutorial by
        Brogan M. Pratt. link: https://www.youtube.com/watch?v=aPNCpZzCTKg
        */






        //Gamepad and OpMode Status Telemetry Data
        telemetry.addData("Status", "Initialized"); //sends "Initialized" to Telemetry
        if (gamepad1.id == Gamepad.ID_UNASSOCIATED) {
            telemetry.addData("Gamepad 1", "Disconnected"); //If controller1 is Disconnected it sends "Disconnected" Telemetry
        } else {
            telemetry.addData("Gamepad 1", "Connected"); //If controller1 is Connected it sends "Connected"
        }

        //Chassis Motor Telemetry Data
        telemetry.addData("FrontLeftMotorPower: ", frontLeft.getPower());
        telemetry.addData("FrontRightMotorPower: ", frontRight.getPower());
        telemetry.addData("BackLeftMotorPower: ", backLeft.getPower());
        telemetry.addData("BackRightMotorPower: ", backRight.getPower());

        //Launcher Intake and Power Telemetry Data
        telemetry.addData("LauncherRightPower: ", launcherRight.getPower());
        telemetry.addData("LauncherLeftPower: ", launcherLeft.getPower());
        telemetry.addData("IntakePower: ", intakeMotor.getPower());

        //Launcher and Intake RPM Telemetry Data
        double launcherRightRPM = (launcherRight.getVelocity() / 28 * 60);
        double launcherLeftRPM = (launcherLeft.getVelocity() / 28 * 60);
        double intakeRPM = (intakeMotor.getVelocity() / 384.5 * 60);
        telemetry.addData("LauncherRightRPM: ", launcherRightRPM);
        telemetry.addData("LauncherLeftRPM: ", launcherLeftRPM);
        telemetry.addData("IntakeRPM: ", intakeRPM); //"max rpm of motor" * motor.getPower = current rpm
        telemetry.update(); // add more telemetry+ data above this line




        waitForStart(); //Waits until Drivers presses Start
        while (opModeIsActive()) {

            //Chassis Power Calculations
            double lStickY = gamepad1.left_stick_y; //Stick Controls (decimal values)
            double lStickX = gamepad1.left_stick_x;
            double rStickX = gamepad1.right_stick_x;
            double rStickY = gamepad1.right_stick_y;
            frontLeft.setPower(lStickY + lStickX - rStickX);
            backLeft.setPower(lStickY - lStickX - rStickX);
            frontRight.setPower(-lStickY - lStickX - rStickX);
            backRight.setPower(-lStickY + lStickX - rStickX);


            //Launcher Modes and Launcher Velocity
            boolean aButton = gamepad1.aWasPressed(); // X on DualSense
            boolean bButton = gamepad1.bWasPressed(); // Circle on DualSense
            if (aButton) {
                stepIndex = (stepIndex + 1) % launcherSpeedSizes.length;
            }
            if (bButton) {
                stepIndex = (stepIndex - 1) % launcherSpeedSizes.length;
            }
            double currentSelectedLauncherVelocity = launcherSpeedSizes[stepIndex];
            launcherRight.setVelocity(currentSelectedLauncherVelocity);
            launcherLeft.setVelocity(-currentSelectedLauncherVelocity);


            /* Calculations for Intake and Launcher RPM  ↓ */
            launcherRightRPM = (launcherRight.getVelocity() / 28 * 60);
            launcherLeftRPM = (launcherLeft.getVelocity() / 28 * 60);
            intakeRPM = (intakeMotor.getVelocity() / 384.5 * 60);

            //Most beautiful calculations for intake ever >:) ↓
            boolean lBumper = gamepad1.left_bumper; // Bumpers (true or false)
            boolean rBumper = gamepad1.right_bumper;
            if (rBumper) {
                intakeMotor.setVelocity(1393.8125);
            } else if (lBumper) {
                intakeMotor.setVelocity(-1393.8125);
            } else {
                intakeMotor.setVelocity(0);
            }

            //Launcher RPM Ready LED ↓
            if (launcherLeftRPM >= stepIndex) {
                gamepad1.setLedColor(0, 255, 0, Gamepad.LED_DURATION_CONTINUOUS);
                gamepad2.setLedColor(0,255,0, Gamepad.LED_DURATION_CONTINUOUS);
            } else {
                // Optional: Turn the LED off or change color if it's NOT ready
                gamepad1.setLedColor(0, 0, 255, Gamepad.LED_DURATION_CONTINUOUS);
                gamepad2.setLedColor(255,0,0, Gamepad. LED_DURATION_CONTINUOUS);
            }
            /* If launcher RPM is above or equal to the set Velocity then it
            switches the LED on the gamepad1 to green and gamepad2 to red to indicate that the
            launcher is ready to intake an artifact
            */




            //Gamepad, OpMode, and Battery Telemetry ↓
            telemetry.addData("Status", "OpModeRunning");
            if (gamepad1.id == Gamepad.ID_UNASSOCIATED) {
                telemetry.addData("Gamepad 1", "Disconnected"); //If controller1 is Disconnected it sends "Disconnected" Telemetry
            } else {
                telemetry.addData("Gamepad 1", "Connected"); //If controller1 is Connected it sends "Connected"
            }
            //Chassis Telemetry Data ↓
            telemetry.addData("FrontLeftMotorPower: ", frontLeft.getPower());
            telemetry.addData("FrontRightMotorPower: ", frontRight.getPower());
            telemetry.addData("BackLeftMotorPower: ", backLeft.getPower());
            telemetry.addData("BackRightMotorPower: ", backRight.getPower());
            //Launcher(s) Telemetry Data ↓
            telemetry.addData("LauncherRightPower: ", launcherRight.getPower());
            telemetry.addData("LauncherLeftPower: ", launcherLeft.getPower());
            telemetry.addData("LauncherRightRPM: ", launcherRightRPM);
            telemetry.addData("LauncherLeftRPM: ", launcherLeftRPM);
            telemetry.addData("LauncherVelocityMode: ", stepIndex);
            telemetry.addData("LauncherTargetVelocity: ", currentSelectedLauncherVelocity);
            //Intake Telemetry Data ↓
            telemetry.addData("IntakePower: ", intakeMotor.getPower());
            telemetry.addData("IntakeRPM: ", intakeRPM);
            //Telemetry Update ↓
            telemetry.update(); // add more telemetry data above this line


            }
        }
    }