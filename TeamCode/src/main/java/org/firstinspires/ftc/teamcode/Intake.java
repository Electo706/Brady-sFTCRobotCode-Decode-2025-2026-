package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Intake {
    private DcMotorEx intakeMotor;
    double intakeRPM = (intakeMotor.getVelocity() / 384.5 * 60);
    public void init(HardwareMap hardwareMap) {
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
        intakeMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
    }
    public void run(boolean lBumper, boolean rBumper) {
        if (rBumper) {
            intakeMotor.setVelocity(1393.8125);
        } else if (lBumper) {
            intakeMotor.setVelocity(-1393.8125);
        } else {
            intakeMotor.setVelocity(0);
        }
    }
    public void telem(Telemetry telemetry) {
        telemetry.addData("IntakeRPM: ", intakeRPM);
    }
}
