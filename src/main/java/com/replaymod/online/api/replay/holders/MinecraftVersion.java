package com.replaymod.online.api.replay.holders;

public enum MinecraftVersion {

    MC_1_8("Minecraft 1.8", "1.8"),
    MC_1_9_4("Minecraft 1.9.4", "1.9.4"),
    MC_1_10_2("Minecraft 1.10.2", "1.10.2"),
    MC_1_11("Minecraft 1.11", "1.11"),
    MC_1_11_2("Minecraft 1.11.2", "1.11.2"),
    MC_1_12("Minecraft 1.12", "1.12"),
    MC_1_12_1("Minecraft 1.12.1", "1.12.1"),
    MC_1_12_2("Minecraft 1.12.2", "1.12.2"),
    MC_1_13_2("Minecraft 1.13.2", "1.13.2"),
    MC_1_14_4("Minecraft 1.14.4", "1.14.4");
    MC_1_15_2("Minecraft 1.15.2", "1.15.2");
    private String niceName, apiName;

    MinecraftVersion(String niceName, String apiName) {
        this.niceName = niceName;
        this.apiName = apiName;
    }

    public String toNiceName() {
        return niceName;
    }

    public String getApiName() {
        return apiName;
    }
}
