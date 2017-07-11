package Utils;

import java.util.List;

import com.baidu.mapapi.model.LatLng;

/**
 * ʵʱ�켣����
 * 
 * 
 */
public class RealtimeTrackData {
    public int status; // ״̬�룬0Ϊ�ɹ�
    public int size; // ���ؽ����������ҳ�����˼�������
    public int total; // �����������������һ���м�����������������
    public List<Entities> entities;
    public String message; // ��Ӧ��Ϣ,��status����������

    public class Entities {
        public String create_time; // ����ʱ�� ��ʽ��ʱ�� ��ʱ��Ϊ�����ʱ��
        public String modify_time; // �޸�ʱ��
        public RealtimePoint realtime_point; // ʵʱ�켣��Ϣ

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getModify_time() {
            return modify_time;
        }

        public void setModify_time(String modify_time) {
            this.modify_time = modify_time;
        }

        public RealtimePoint getRealtime_point() {
            return realtime_point;
        }

        public void setRealtime_point(RealtimePoint realtime_point) {
            this.realtime_point = realtime_point;
        }

    }

    public class RealtimePoint {
        public List<Double> location;// ��γ�� Array �ٶȼ�������
        public String loc_time;// ��trackʵʱ����ϴ�ʱ�� UNIXʱ��� ��ʱ��Ϊ�û��ϴ���ʱ��

        public List<Double> getLocation() {
            return location;
        }

        public void setLocation(List<Double> location) {
            this.location = location;
        }

        public String getLoc_time() {
            return loc_time;
        }

        public void setLoc_time(String loc_time) {
            this.loc_time = loc_time;
        }

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Entities> getEntities() {
        return entities;
    }

    public void setEntities(List<Entities> entities) {
        this.entities = entities;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LatLng getRealtimePoint() {

        if (entities.get(0).realtime_point == null) {
            return null;
        }

        List<Double> location = entities.get(0).realtime_point.location;
        if (Math.abs(location.get(0) - 0.0) < 0.01 && Math.abs(location.get(1) - 0.0) < 0.01) {
            return null;
        } else {
            return new LatLng(location.get(1), location.get(0));
        }
    }
}

