package cn.tuyucheng.taketoday.dynamic.autowire;

import org.springframework.stereotype.Service;

@Service("CNregionService")
public class CNRegionService implements RegionService {
    @Override
    public boolean isServerActive(int serverId) {
        return false;
    }

    @Override
    public String getISOCountryCode() {
        return "CN";
    }
}