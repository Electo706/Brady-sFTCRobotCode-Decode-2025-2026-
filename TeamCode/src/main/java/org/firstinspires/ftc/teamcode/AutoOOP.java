package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.logging.Handler;

@Autonomous(name = "auto")
public class AutoOOP extends OpMode {
    @Override
    public void init() {

    }
    @Override
    public void loop() {
        Drivetrain Drive = new Drivetrain();
        Intake Intake = new Intake();
        Launcher Launcher = new Launcher();

        Drive.init(hardwareMap);
        Intake.init(hardwareMap);
        Launcher.init(hardwareMap);

        Drive.telem(telemetry);
        Launcher.telem(telemetry);
        Intake.telem(telemetry);
        telemetry.update();
    }
}

