package com.smart.gankmvp.http.result;

import com.smartcentury.kcwork.R;
import com.smartcentury.kcwork.util.GetUtil;


    /**
     * 请求状态
     * @author vincentkin038
     */
    public class StatusResult {
        /**
         * 状态码
         */
        private int status;
        /**
         * 错误描述
         */
        private String desc;
        /**
         * 是否成功
         */
        private boolean isSuccess;

        /**
         * 静态对象
         */
        private static final StatusResult STATUS_RESULT = new StatusResult();

        /**
         * 通过状态码获取提示
         *
         * @param status 状态码
         * @return 描述对象
         */
        public static StatusResult judgeStatus(int status) {

            String desc = "";
            boolean isSuccess = false;
            switch (status) {
                case 200:
                    desc = GetUtil.INSTANCE.getString(R.string.success);
                    isSuccess = true;
                    break;
                default:
                    break;

            }
            STATUS_RESULT.setStatus(status);
            STATUS_RESULT.setDesc(desc);
            STATUS_RESULT.setSuccess(isSuccess);
            return STATUS_RESULT;
        }

        public static StatusResult judgeStatus(int status,String desc) {

            boolean isSuccess = false;
            switch (status) {
                case 201:
                case 200:
                    desc = GetUtil.INSTANCE.getString(R.string.success);
                    isSuccess = true;
                    break;
                default:
                    break;

            }
            STATUS_RESULT.setStatus(status);
            STATUS_RESULT.setDesc(desc);
            STATUS_RESULT.setSuccess(isSuccess);
            return STATUS_RESULT;
        }


        private void setStatus(int status) {
            this.status = status;
        }

        public String getDesc() {
            return desc;
        }

        private void setDesc(String desc) {
            this.desc = desc;
        }

        private void setSuccess(boolean success) {
            isSuccess = success;
        }

        @Override
        public String toString() {
            return "StatusResult{" +
                    "status=" + status +
                    ", desc='" + desc + '\'' +
                    ", isSuccess=" + isSuccess +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof StatusResult)) {
                return false;
            }

            StatusResult that = (StatusResult) o;

            if (status != that.status) {
                return false;
            }
            if (isSuccess != that.isSuccess) {
                return false;
            }
            return desc != null ? desc.equals(that.desc) : that.desc == null;
        }

        @Override
        public int hashCode() {
            int result = status;
            result = 31 * result + (desc != null ? desc.hashCode() : 0);
            result = 31 * result + (isSuccess ? 1 : 0);
            return result;
        }
}
