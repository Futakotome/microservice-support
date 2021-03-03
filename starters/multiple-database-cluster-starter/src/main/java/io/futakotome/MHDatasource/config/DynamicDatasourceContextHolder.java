package io.futakotome.MHDatasource.config;

public final class DynamicDatasourceContextHolder {
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDatasource(final String datasourceKey) {
        CONTEXT_HOLDER.set(datasourceKey);
    }

    public static String getDatasourceKey() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDatasourceKey() {
        CONTEXT_HOLDER.remove();
    }
}
