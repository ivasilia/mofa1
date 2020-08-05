package softuni.ivasi.mofa.common.statistics;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PathService {

    private AtomicInteger countRequestsToProjects = new AtomicInteger(0);
    private AtomicInteger countRequestsToItems = new AtomicInteger(0);

    public void incRequestsToProjectsCount() {
        countRequestsToProjects.incrementAndGet();
        System.out.println();
    }

    public int getCountRequestsToProjects() {
        return countRequestsToProjects.intValue();
    }

    public void incRequestsToItemsCount() {
        countRequestsToItems.incrementAndGet();
    }

    public int getCountRequestsToItems() {
        return countRequestsToItems.intValue();
    }
}
