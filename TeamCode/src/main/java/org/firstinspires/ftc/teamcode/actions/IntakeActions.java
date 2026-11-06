package org.firstinspires.ftc.teamcode.actions;

import com.teamundefined.defined.Action;
import com.teamundefined.defined.generic.ToggleAction;
import com.teamundefined.defined.runner.ActionRunner;
import com.teamundefined.defined.runner.WhilePressedAction;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Subsystem;

import java.util.function.BooleanSupplier;

public class IntakeActions {
    public static Action startIntake(Robot r, boolean reversed) {
        return Action.oneShot("intake_start", now -> r.intake.start(reversed));
    }

    public static Action stopIntake(Robot r) {
        return Action.oneShot("intake_stop", now -> r.intake.stop());
    }

    public static Action toggleIntake(Robot r, BooleanSupplier button) {
        return ToggleAction.onPress(
                "intake_toggle",
                button,
                startIntake(r, false),
                stopIntake(r)
        ).withStateProbe(() -> r.intake.isPowered());
    }

    public static Action manualIntake(Robot r, BooleanSupplier button, ActionRunner runner) {
        return new WhilePressedAction(
                "manual_intake",
                button,
                runner,
                () -> startIntake(r, false).requires(Subsystem.INTAKE),
                () -> stopIntake(r).requires(Subsystem.INTAKE)
        );
    }
}
