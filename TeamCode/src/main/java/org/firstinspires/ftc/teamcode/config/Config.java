package org.firstinspires.ftc.teamcode.config;

import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class Config {
    // TODO A static class where every tunable parameter is found. Recommend child classes for every category.
    //  @Configurable for every class to access each parameter from the Panels Dashboard or @Config for FTC Dashboard.
    public enum AllianceColor {RED, BLUE}

    public static AllianceColor ALLIANCE_COLOR = AllianceColor.RED;

    @Configurable
    public static class Intake {
        public static double IN_POWER = 1;
        public static double IDLE_POWER = 0;
        public static double OUT_POWER = -1;
    }

    @Configurable
    public static class Time {
        public static long BALLS_EXIT_DELAY = 500;
        public static long SHOOTING_SEQUENCE_TIMEOUT = 5000;
    }

    @Configurable
    public static class Debug {
        public static boolean TELEMETRY_ACTIVE = true;
        public static boolean PROFILER_ACTIVE = true;
        public static boolean POWER_INFO_ACTIVE = true;
        public static boolean SYSTEM_METRICS_ACTIVE = true;
        public static int TELEMETRY_UPDATE_CYCLES = 50;
    }

    @Configurable
    public static class Power {
        public static double INTAKE_AMPS = 0;
    }

    @Configurable
    public static class  UpdateIntervals {
        public static long INTAKE_CURRENT_UPDATE_INTERVAL = 250;
    }

    @Configurable
    public static class Hardware {
        // TODO Easy to modify any appearance of hardware map names.
        //  (Useful if you have multiple tuning OpModes where you do not initialize a full robot, only fragments of it.)
        public static String LB_DRIVE_MOTOR_NAME = "leftBack";
        public static String LF_DRIVE_MOTOR_NAME = "leftFront";
        public static String RB_DRIVE_MOTOR_NAME = "rightBack";
        public static String RF_DRIVE_MOTOR_NAME = "rightFront";
        public static String INTAKE_MOTOR_NAME = "intake";
    }
}
