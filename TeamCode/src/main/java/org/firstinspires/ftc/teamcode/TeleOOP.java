package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@TeleOp

public class TeleOOP extends OpMode {
    @Override
    public void init() {            //Waits till Driver presses Initialize
        Drivetrain Drive = new Drivetrain();
        Intake Intake = new Intake();
        Launcher Launcher = new Launcher();

        Drive.init(hardwareMap);
        Intake.init(hardwareMap);
        Launcher.init(hardwareMap);

        Drive.telem(telemetry);
        Intake.telem(telemetry);
        Launcher.telem(telemetry);
        telemetry.update();
    }
    @Override
    public void loop() {
        Drivetrain Drive = new Drivetrain();
        Intake Intake = new Intake();
        Launcher Launch = new Launcher();

        Drive.init(hardwareMap);
        Intake.init(hardwareMap);
        Launch.init(hardwareMap);

        Drive.run(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        Launch.run(gamepad1.right_trigger, gamepad1.left_trigger,
                gamepad1.aWasPressed(), gamepad1.bWasPressed());
        Intake.run(gamepad1.left_bumper, gamepad1.right_bumper);

        Drive.telem(telemetry);
        Launch.telem(telemetry);
        Intake.telem(telemetry);
        telemetry.update();
        Launch.LED(true, gamepad1, gamepad2);
    }
}
