package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.Config;

public class Intake {
    // TODO Modify these subsystem classes to match your hardware configuration.
    //  A subsystem class should handle control logic (PIDs, State machines), not gamepad inputs.
    public DcMotorEx motor;
    double _power = 0;
    public Intake(HardwareMap hw) {
        this.motor = hw.get(DcMotorEx.class, Config.Hardware.INTAKE_MOTOR_NAME);
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void start(boolean reversed) {
        _power = reversed ? Config.Intake.OUT_POWER : Config.Intake.IN_POWER;
    }

    public void stop() {
        _power = Config.Intake.IDLE_POWER;
    }

    public boolean isPowered() {
        return Math.abs(_power) > 0.005; // reversed (negative) power also counts as powered
    }

    public void update() {
        motor.setPower(_power);
    }
}
