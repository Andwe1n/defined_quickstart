package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.teamundefined.defined.HardwareScheduler;
import com.teamundefined.defined.SectionProfiler;
import com.teamundefined.defined.SystemMonitor;
import com.teamundefined.defined.ftc.AndroidMetrics;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.config.Config;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

import java.util.List;

public class Robot extends com.teamundefined.defined.ftc.Robot {
    // TODO Master class for your subsystems. Replace with your own.
    public enum Section {PRE_UPDATE, SUBSYSTEMS}
    public enum Read {INTAKE_CURRENT}
    public Intake intake;
    public Drive drive;

    // Profiler - to measure how much time any code section takes (optional)
    // Not final: rebuilt in start() so the pre-start menu's Debug.PROFILER_ACTIVE choice takes effect.
    public SectionProfiler<Section> profiler = new SectionProfiler<>(Config.Debug.PROFILER_ACTIVE);

    // System monitor - to display cycle time, cpu usage and other information (optional)
    public final SystemMonitor systemMonitor = new SystemMonitor(0.8);

    // Hardware scheduler - I2c only, make only one I2c transaction per loop, preventing I2c spikes and errors (optional)
    public final HardwareScheduler<Read> hardware = new HardwareScheduler<>();

    private final List<LynxModule> allHubs;
    public Robot(HardwareMap hw) {
        intake = new Intake(hw);
        drive = new Drive(hw);
        allHubs = hw.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }

        hardware.register(Read.INTAKE_CURRENT, "intake_amps", Config.UpdateIntervals.INTAKE_CURRENT_UPDATE_INTERVAL,
                          () -> Config.Power.INTAKE_AMPS = intake.motor.getCurrent(CurrentUnit.AMPS), Config.Debug.POWER_INFO_ACTIVE);

        AndroidMetrics.addCpu(systemMonitor);
        systemMonitor.addStandardMetrics();
        systemMonitor.enabledWhen(() -> Config.Debug.SYSTEM_METRICS_ACTIVE);
    }

    @Override
    public void init() {
        // Once during INIT, after build. Zero encoders, set modes and other initialization stuff
    }

    @Override
    public void initUpdate() {
        // Every cycle between INIT and START. Vision warm-up, servo holds while menu is open
    }

    @Override
    public void start(boolean isTeleOp) {
        // Once when match begins. Reset state, choose TeleOp vs Auto behavior

        // Apply debug flags chosen in the pre-start menu (they were read at construction, before the menu ran)
        profiler = new SectionProfiler<>(Config.Debug.PROFILER_ACTIVE);
        hardware.setEnabled("intake_amps", Config.Debug.POWER_INFO_ACTIVE);
    }

    @Override
    public void preUpdate(long nowMs) {
        // First half of every loop. Clear bulk cache, update odometry — BEFORE OpMode reads state
        profiler.start(Section.PRE_UPDATE);
        for (LynxModule hub : allHubs) hub.clearBulkCache();
        hardware.update(nowMs);
        systemMonitor.update(nowMs);
        profiler.stop(Section.PRE_UPDATE);
    }

    @Override
    public void setOpModeTime(double seconds) {
        // Every loop (optional). Auto timing ("stop at t=25s")
    }

    @Override
    public void update(long nowMs) {
        // Second half of every loop. Subsystem state machines, motor/servo writes
        profiler.time(Section.SUBSYSTEMS, () -> {
            intake.update();
        });
    }

    @Override
    public void stop() {
        // Once on OpMode end / e-stop. Shutdown threads, zero power
    }

    public boolean noBallsDetected() {
        // Sensor logic here
        return true;
    }
}
