package org.firstinspires.ftc.teamcode.actions;

import android.util.Log;

import com.teamundefined.defined.Action;
import com.teamundefined.defined.generic.ParallelAction;
import com.teamundefined.defined.generic.SequentialAction;
import com.teamundefined.defined.generic.WaitAction;
import com.teamundefined.defined.generic.WaitUntilAction;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.config.Config;
import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

import java.util.ArrayList;
import java.util.List;

public class ShootingActions {
    public static Action shootingSequence(Robot r) {
        List<Action> actions = new ArrayList<>();
        actions.add(FlywheelActions.flywheelSpinUp(r));
        actions.add(ParallelAction.all(
                "parallel_start_subsystems",
                IntakeActions.startIntake(r, false),
                TransferActions.startTransfer(r),
                TransferActions.unlockTransfer(r))
        );
        actions.add(WaitUntilAction.until("wait_for_balls_to_exit", r::noBallsDetected)); // if using sensors
        actions.add(WaitAction.ms("balls_exit_delay", Config.Time.BALLS_EXIT_DELAY)); // if using time-based
        actions.add(ParallelAction.all(
                "parallel_stop_subsystems",
                IntakeActions.stopIntake(r),
                TransferActions.stopTransfer(r),
                TransferActions.lockTransfer(r))
        );
        return new SequentialAction("shooting_sequence", actions)
                .withTimeout(Config.Time.SHOOTING_SEQUENCE_TIMEOUT)
                .withOnComplete(now -> Log.i("SHOOTING_SEQ", "Shooting sequence completed."))
                .requires(Subsystem.DRIVE, Subsystem.INTAKE);
    }
}
