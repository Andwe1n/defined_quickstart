package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.teamundefined.defined.TelemetrySnapshot;
import com.teamundefined.defined.ftc.RobotOpMode;
import com.teamundefined.defined.generic.Continuous;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.actions.IntakeActions;
import org.firstinspires.ftc.teamcode.actions.ShootingActions;
import org.firstinspires.ftc.teamcode.config.Config;

@TeleOp
public class MainTeleOp extends RobotOpMode<Robot> {
    @Override
    protected Robot createRobot() {
        return new Robot(hardwareMap);
    }

    @Override
    protected void onRobotInit() {
        // `robot` is already built by createRobot() and robot.init() has run before this is called.
        enablePreStartMenu( // Interactive menu while the robot is in INIT state that lets you change behaviour without a new code upload.
                Config.class,
                "Config.ALLIANCE_COLOR",
                "Debug.TELEMETRY_ACTIVE",
                "Debug.PROFILER_ACTIVE",
                "Debug.POWER_INFO_ACTIVE"
        );
        runner.addMonitor(IntakeActions.toggleIntake(robot, () -> gamepad1.squareWasReleased()));
        runner.addMonitor(IntakeActions.manualIntake(robot, () -> gamepad1.right_trigger_pressed, runner));
        runner.addMonitor(Continuous.monitor("shooting_monitor", () -> gamepad1.circleWasReleased(), now -> runner.startGroup(ShootingActions.shootingSequence(robot))));
        // Add other monitors here
    }

    @Override
    protected void onLoop(long nowMs) {
        // Driver controls and per-cycle logic
        robot.drive.updateTeleOpInputs(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
    }

    @Override
    protected void fillSnapshot(TelemetrySnapshot snapshot) {
        // Add telemetry. Formatted on a background thread to maximize performance.
        snapshot.put("Intake On", robot.intake.isPowered() ? "YES" : "NO");
        snapshot.putDouble("Intake amps", Config.Power.INTAKE_AMPS, 2);
        snapshot.put("System Metrics", robot.systemMonitor.getFormattedStats());
        snapshot.put("Profiler", robot.profiler.getFormattedStats());
    }

    @Override
    protected boolean isTelemetryEnabled() {
        // A switch for telemetry ON/OFF. Default true.
        return Config.Debug.TELEMETRY_ACTIVE;
    }

    @Override
    protected int telemetryRefreshCycles() {
        // Number of cycles between telemetry updates. Default 20.
        return Config.Debug.TELEMETRY_UPDATE_CYCLES;
    }
}
