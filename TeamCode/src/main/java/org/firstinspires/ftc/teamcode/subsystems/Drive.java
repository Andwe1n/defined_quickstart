package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.Config;

public class Drive {
    // TODO Should be replaced by Pedro Pathing's follower or something similar.
    DcMotor leftBack, rightBack, leftFront, rightFront;

    public Drive(HardwareMap hw) {
        this.leftBack = hw.get(DcMotorEx.class, Config.Hardware.LB_DRIVE_MOTOR_NAME);
        this.rightBack = hw.get(DcMotorEx.class, Config.Hardware.RB_DRIVE_MOTOR_NAME);
        this.leftFront = hw.get(DcMotorEx.class, Config.Hardware.LF_DRIVE_MOTOR_NAME);
        this.rightFront = hw.get(DcMotorEx.class, Config.Hardware.RF_DRIVE_MOTOR_NAME);

        this.leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        this.leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        this.leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void updateTeleOpInputs(double y, double x, double rx) {
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        leftBack.setPower(backLeftPower);
        leftFront.setPower(frontLeftPower);
        rightBack.setPower(backRightPower);
        rightFront.setPower(frontRightPower);
    }
}
