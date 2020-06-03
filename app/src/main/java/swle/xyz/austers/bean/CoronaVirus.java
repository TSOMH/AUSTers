package swle.xyz.austers.bean;
/**
*Created by TSOMH on 2020/6/2$
*Description:
*
*/
import java.util.List;

public class CoronaVirus {

   private List<Results> results;
   private boolean success;


   public void setResults(List<Results> results) {
      this.results = results;
   }
   public List<Results> getResults() {
      return results;
   }

   public void setSuccess(boolean success) {
      this.success = success;
   }
   public boolean getSuccess() {
      return success;
   }


   public static class Results {

      private int currentConfirmedCount;
      private int currentConfirmedIncr;
      private long confirmedCount;
      private int confirmedIncr;
      private int suspectedCount;
      private int suspectedIncr;
      private long curedCount;
      private int curedIncr;
      private int deadCount;
      private int deadIncr;
      private int seriousCount;
      private int seriousIncr;
      private GlobalStatistics globalStatistics;
      private String generalRemark;
      private String remark1;
      private String remark2;
      private String remark3;
      private String remark4;
      private String remark5;
      private String note1;
      private String note2;
      private String note3;
      private long updateTime;
      public void setCurrentConfirmedCount(int currentConfirmedCount) {
         this.currentConfirmedCount = currentConfirmedCount;
      }
      public int getCurrentConfirmedCount() {
         return currentConfirmedCount;
      }

      public void setCurrentConfirmedIncr(int currentConfirmedIncr) {
         this.currentConfirmedIncr = currentConfirmedIncr;
      }
      public int getCurrentConfirmedIncr() {
         return currentConfirmedIncr;
      }

      public void setConfirmedCount(long confirmedCount) {
         this.confirmedCount = confirmedCount;
      }
      public long getConfirmedCount() {
         return confirmedCount;
      }

      public void setConfirmedIncr(int confirmedIncr) {
         this.confirmedIncr = confirmedIncr;
      }
      public int getConfirmedIncr() {
         return confirmedIncr;
      }

      public void setSuspectedCount(int suspectedCount) {
         this.suspectedCount = suspectedCount;
      }
      public int getSuspectedCount() {
         return suspectedCount;
      }

      public void setSuspectedIncr(int suspectedIncr) {
         this.suspectedIncr = suspectedIncr;
      }
      public int getSuspectedIncr() {
         return suspectedIncr;
      }

      public void setCuredCount(long curedCount) {
         this.curedCount = curedCount;
      }
      public long getCuredCount() {
         return curedCount;
      }

      public void setCuredIncr(int curedIncr) {
         this.curedIncr = curedIncr;
      }
      public int getCuredIncr() {
         return curedIncr;
      }

      public void setDeadCount(int deadCount) {
         this.deadCount = deadCount;
      }
      public int getDeadCount() {
         return deadCount;
      }

      public void setDeadIncr(int deadIncr) {
         this.deadIncr = deadIncr;
      }
      public int getDeadIncr() {
         return deadIncr;
      }

      public void setSeriousCount(int seriousCount) {
         this.seriousCount = seriousCount;
      }
      public int getSeriousCount() {
         return seriousCount;
      }

      public void setSeriousIncr(int seriousIncr) {
         this.seriousIncr = seriousIncr;
      }
      public int getSeriousIncr() {
         return seriousIncr;
      }

      public void setGlobalStatistics(GlobalStatistics globalStatistics) {
         this.globalStatistics = globalStatistics;
      }
      public GlobalStatistics getGlobalStatistics() {
         return globalStatistics;
      }

      public void setGeneralRemark(String generalRemark) {
         this.generalRemark = generalRemark;
      }
      public String getGeneralRemark() {
         return generalRemark;
      }

      public void setRemark1(String remark1) {
         this.remark1 = remark1;
      }
      public String getRemark1() {
         return remark1;
      }

      public void setRemark2(String remark2) {
         this.remark2 = remark2;
      }
      public String getRemark2() {
         return remark2;
      }

      public void setRemark3(String remark3) {
         this.remark3 = remark3;
      }
      public String getRemark3() {
         return remark3;
      }

      public void setRemark4(String remark4) {
         this.remark4 = remark4;
      }
      public String getRemark4() {
         return remark4;
      }

      public void setRemark5(String remark5) {
         this.remark5 = remark5;
      }
      public String getRemark5() {
         return remark5;
      }

      public void setNote1(String note1) {
         this.note1 = note1;
      }
      public String getNote1() {
         return note1;
      }

      public void setNote2(String note2) {
         this.note2 = note2;
      }
      public String getNote2() {
         return note2;
      }

      public void setNote3(String note3) {
         this.note3 = note3;
      }
      public String getNote3() {
         return note3;
      }

      public void setUpdateTime(long updateTime) {
         this.updateTime = updateTime;
      }
      public long getUpdateTime() {
         return updateTime;
      }


      public class GlobalStatistics {

         private long currentConfirmedCount;
         private long confirmedCount;
         private long curedCount;
         private long deadCount;
         private int currentConfirmedIncr;
         private int confirmedIncr;
         private int curedIncr;
         private int deadIncr;
         public void setCurrentConfirmedCount(long currentConfirmedCount) {
            this.currentConfirmedCount = currentConfirmedCount;
         }
         public long getCurrentConfirmedCount() {
            return currentConfirmedCount;
         }

         public void setConfirmedCount(long confirmedCount) {
            this.confirmedCount = confirmedCount;
         }
         public long getConfirmedCount() {
            return confirmedCount;
         }

         public void setCuredCount(long curedCount) {
            this.curedCount = curedCount;
         }
         public long getCuredCount() {
            return curedCount;
         }

         public void setDeadCount(long deadCount) {
            this.deadCount = deadCount;
         }
         public long getDeadCount() {
            return deadCount;
         }

         public void setCurrentConfirmedIncr(int currentConfirmedIncr) {
            this.currentConfirmedIncr = currentConfirmedIncr;
         }
         public int getCurrentConfirmedIncr() {
            return currentConfirmedIncr;
         }

         public void setConfirmedIncr(int confirmedIncr) {
            this.confirmedIncr = confirmedIncr;
         }
         public int getConfirmedIncr() {
            return confirmedIncr;
         }

         public void setCuredIncr(int curedIncr) {
            this.curedIncr = curedIncr;
         }
         public int getCuredIncr() {
            return curedIncr;
         }

         public void setDeadIncr(int deadIncr) {
            this.deadIncr = deadIncr;
         }
         public int getDeadIncr() {
            return deadIncr;
         }

      }

   }


}