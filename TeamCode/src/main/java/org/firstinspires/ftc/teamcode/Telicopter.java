package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;


@TeleOp

public class Telicopter extends OpMode {
    @Override
    public void init() {            //Waits till Driver presses Initialize

        Drivetrain Drive = new Drivetrain();
        Intake Intake = new Intake();
        Launcher Launcher = new Launcher();

        Drive.init(hardwareMap);
        Intake.init(hardwareMap);
        Launcher.init(hardwareMap);



        //Gamepad and OpMode Status Telemetry Data
        telemetry.addData("Status", "Initialized"); //sends "Initialized" to Telemetry
        if (gamepad1.id == Gamepad.ID_UNASSOCIATED) {
            telemetry.addData("Gamepad 1", "Disconnected"); //If controller1 is Disconnected it sends "Disconnected" Telemetry
        } else {
            telemetry.addData("Gamepad 1", "Connected"); //If controller1 is Connected it sends "Connected"
        }





        //Launcher and Intake RPM Telemetry Data
        double intakeRPM = (intakeMotor.getVelocity() / 384.5 * 60);
        telemetry.addData("IntakeRPM: ", intakeRPM); //"max rpm of motor" * motor.getPower = current rpm
        telemetry.update(); // add more telemetry+ data above this line
    }



        @Override
         public void loop() {
            Drivetrain Drive = new Drivetrain();
            Intake Intake = new Intake();
            Launcher Launcher = new Launcher();

            Drive.init(hardwareMap);
            Intake.init(hardwareMap);
            Launcher.init(hardwareMap);

            Drive.power(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            Launcher.setLauncherSpeed(gamepad1.right_trigger, gamepad1.left_trigger,
                    gamepad1.aWasPressed(), gamepad1.bWasPressed());

            Drive.telem(telemetry);
            Launcher.telem(telemetry);
            Launcher.LED(true,gamepad1,gamepad2);






            intakeRPM = (intakeMotor.getVelocity() / 384.5 * 60);

            //Most beautiful calculations for intake ever >:) ↓ (Don't try to change it)
            boolean lBumper = gamepad1.left_bumper; // Bumpers (true or false)
            boolean rBumper = gamepad1.right_bumper;
            if (rBumper) {
                intakeMotor.setVelocity(1393.8125);
            } else if (lBumper) {
                intakeMotor.setVelocity(-1393.8125);
            } else {
                intakeMotor.setVelocity(0);
            }


            //Gamepad, OpMode, and Battery Telemetry ↓
            telemetry.addData("Status", "OpModeRunning");
            if (gamepad1.id == Gamepad.ID_UNASSOCIATED) {
                telemetry.addData("Gamepad 1", "Disconnected"); //If controller1 is Disconnected it sends "Disconnected" Telemetry
            } else {
                telemetry.addData("Gamepad 1", "Connected"); //If controller1 is Connected it sends "Connected"
            }

            telemetry.addData("IntakePower: ", intakeMotor.getPower());
            telemetry.addData("IntakeRPM: ", intakeRPM);
            //Telemetry Update ↓
            telemetry.update(); // add more telemetry data above this line


            }
        }
    }