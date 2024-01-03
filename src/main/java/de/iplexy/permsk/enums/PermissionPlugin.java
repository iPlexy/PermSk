package de.iplexy.permsk.enums;

public enum PermissionPlugin {

    LUCKPERMS("LuckPerms", "LuckApi"),
    PERMISSIONSEX("PermissionsEx", "PexApi"),
    GROUPMANAGER("GroupManager", "GroupManagerApi"),
    ULTIMATEPERMISSIONS("UltimatePermissions", "UltimateApi");

    private String name;
    private String apiClass;

    PermissionPlugin(String name, String apiClass) {
        this.name = name;
        this.apiClass = apiClass;
    }

    public String getName() {
        return name;
    }

    public String getApiClass() {
        return apiClass;
    }
}
