package main;

import java.util.ArrayList;
import java.util.List;

import dao.WorkerDAOImplementation;
import model.Worker;

public class App {
    public static void main(String[] args) throws Exception {
        WorkerDAOImplementation wDao = new WorkerDAOImplementation();
        List<Worker> workerList = wDao.getWorkers();
        workerList.forEach(System.out::println);
        Worker worker = wDao.getWorker(1);

        System.out.println(worker);

        worker.setWorkerId(2);
        wDao.update(worker);
        System.out.println(wDao.getWorker(2));
    }
}
