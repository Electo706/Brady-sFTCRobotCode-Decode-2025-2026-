package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.VoltageSensor;


@TeleOp(name = "Basic Drive", group = "Test")
public class BradysJavaOpMode extends LinearOpMode {
    private DcMotor frontLeft, frontRight;
    private DcMotor backLeft, backRight;
    private DcMotorEx intakeMotor;
    private DcMotorEx launcherRight, launcherLeft;


    @Override
    public void runOpMode() {            //Waits till Driver presses Initialize

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        launcherRight = hardwareMap.get(DcMotorEx.class, "launcherRight");
        launcherLeft = hardwareMap.get(DcMotorEx.class, "launcherLeft");
        VoltageSensor voltageSensor = hardwareMap.voltageSensor.iterator().next();

        intakeMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcherLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcherRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);

        double batteryVoltage = voltageSensor.getVoltage();
        double launcherRightRPM = (launcherRight.getVelocity() / 28 * 60);
        double launcherLeftRPM = (launcherLeft.getVelocity() / 28 * 60);
        double intakeRPM = (intakeMotor.getVelocity() / 384.5 * 60);
        double[] launcherSpeedSizes = {0, 280, 560, 840, 1120, 1400, 1680, 1960, 2240, 2520, 2800,};
        int stepIndex = 0;












        //===============================================================================================//
        //=========================================Telemetry Data========================================//
        //===============================================================================================//

        //telemetry.addData("Battery Voltage", "%.2f V", batteryVoltage); //Sends Voltage of Robot to Telemetry
        telemetry.addData("Status", "Initialized"); //sends "Initialized" to Telemetry
        if (batteryVoltage < 12.0) { //if voltage is lower than 12V then it sends a warning
            telemetry.addData("WARNING", "Battery Low");
        }


        if (gamepad1.id == Gamepad.ID_UNASSOCIATED) {
            telemetry.addData("Gamepad 1", "Disconnected"); //If controller1 is Disconnected it sends "Disconnected" Telemetry
        } else {
            telemetry.addData("Gamepad 1", "Connected"); //If controller1 is Connected it sends "Connected"
        }

        telemetry.addData("FrontLeftMotorPower: ", frontLeft.getPower());
        telemetry.addData("FrontRightMotorPower: ", frontRight.getPower());
        telemetry.addData("BackLeftMotorPower: ", backLeft.getPower());
        telemetry.addData("BackRightMotorPower: ", backRight.getPower());
        telemetry.addData("LauncherRightPower: ", launcherRight.getPower());
        telemetry.addData("LauncherLeftPower: ", launcherLeft.getPower());
        telemetry.addData("LauncherRightRPM: ", launcherRightRPM);
        telemetry.addData("LauncherLeftRPM: ", launcherLeftRPM);
        telemetry.addData("IntakePower: ", intakeMotor.getPower());
        telemetry.addData("IntakeRPM: ", intakeRPM); //"max rpm of motor" * motor.getPower = current rpm

        telemetry.update(); // add more telemetry+ data above this line




        waitForStart(); //Waits until Drivers presses Start
        while (opModeIsActive()) {
            double lStickY = gamepad1.left_stick_y; //Stick Controls (decimal values)
            double lStickX = gamepad1.left_stick_x;
            double rStickX = gamepad1.right_stick_x;
            double rStickY = gamepad1.right_stick_y;
            boolean lBumper = gamepad1.left_bumper; // Bumpers (true or false)
            boolean rBumper = gamepad1.right_bumper;
            double lTrigger = gamepad1.left_trigger; //Triggers (decimal values)
            double rTrigger = gamepad1.right_trigger;
            boolean aButton = gamepad1.aWasPressed(); // X on DualSense
            boolean bButton = gamepad1.bWasPressed(); // Circle on DualSense
            boolean xButton = gamepad1.x; // Square on DualSense
            boolean yButton = gamepad1.y; // Triangle on DualSense
            double rTriggerValue = gamepad1.right_trigger;
            double lTriggerValue = gamepad1.left_trigger;




            frontLeft.setPower(lStickY + lStickX - rStickX); //Calculations for DriveTrain Motors
            backLeft.setPower(lStickY - lStickX - rStickX);
            frontRight.setPower(-lStickY - lStickX - rStickX);
            backRight.setPower(-lStickY + lStickX - rStickX);



            if (aButton) {
                stepIndex = (stepIndex + 1) % launcherSpeedSizes.length;
            }
            if (bButton) {
                stepIndex = (stepIndex - 1) % launcherSpeedSizes.length;
            }
            double currentSelectedLauncherVelocity = launcherSpeedSizes[stepIndex];


            launcherRight.setVelocity(currentSelectedLauncherVelocity);//Velocity math
            launcherLeft.setVelocity(-currentSelectedLauncherVelocity);




            launcherRightRPM = (launcherRight.getVelocity() / 28 * 60); //Calculates rpm of intake and launcher motors
            launcherLeftRPM = (launcherLeft.getVelocity() / 28 * 60);
            intakeRPM = (intakeMotor.getVelocity() / 384.5 * 60);

            if (rBumper) { //Most beautiful calculations for intake ever >:)
                intakeMotor.setVelocity(1393.8125);
            } else if (lBumper) {
                intakeMotor.setVelocity(-1393.8125);
            } else {
                intakeMotor.setVelocity(0);
            }

            if (launcherLeftRPM >= 5400) {
                gamepad1.setLedColor(0, 255, 0, Gamepad.LED_DURATION_CONTINUOUS);
                gamepad2.setLedColor(0,255,0, Gamepad.LED_DURATION_CONTINUOUS);
            } else {
                // Optional: Turn the LED off or change color if it's NOT ready
                gamepad1.setLedColor(0, 0, 255, Gamepad.LED_DURATION_CONTINUOUS);
                gamepad2.setLedColor(255,0,0, Gamepad. LED_DURATION_CONTINUOUS);
            }   /* if launcher rpm is above 5400rpm then it
            switchers the LED on the controller to green to indicate It's ready to launch the artifact  */













                //===============================================================================================//
                //=========================================Telemetry Data========================================//
                //===============================================================================================//

                //telemetry.addData("Battery Voltage", "%.2f V", batteryVoltage); //Sends Voltage of Robot to Telemetry
                telemetry.addData("Status", "OpModeRunning");
                if (batteryVoltage < 12.0) { //if voltage is lower than 12V then it sends a warning
                    telemetry.addData("WARNING", "Battery Low");
                }
                if (gamepad1.id == Gamepad.ID_UNASSOCIATED) {
                    telemetry.addData("Gamepad 1", "Disconnected"); //If controller1 is Disconnected it sends "Disconnected" Telemetry
                } else {
                    telemetry.addData("Gamepad 1", "Connected"); //If controller1 is Connected it sends "Connected"
                }

                telemetry.addData("FrontLeftMotorPower: ", frontLeft.getPower());
                telemetry.addData("FrontRightMotorPower: ", frontRight.getPower());
                telemetry.addData("BackLeftMotorPower: ", backLeft.getPower());
                telemetry.addData("BackRightMotorPower: ", backRight.getPower());
                telemetry.addData("LauncherRightPower: ", launcherRight.getPower());
                telemetry.addData("LauncherLeftPower: ", launcherLeft.getPower());
                telemetry.addData("LauncherRightRPM: ", launcherRightRPM);
                telemetry.addData("LauncherLeftRPM: ", launcherLeftRPM);
                telemetry.addData("IntakePower: ", intakeMotor.getPower());
                telemetry.addData("IntakeRPM: ", intakeRPM);

                telemetry.update(); // add more telemetry data above this line


            }
        }
    }