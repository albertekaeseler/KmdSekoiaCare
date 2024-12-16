package com.jmc.kmdsekoiacarepath.Controllers;

import com.jmc.kmdsekoiacarepath.Models.Job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
//it helps us to organize and retrieve data by associating key with values.F.x storing job ID's(keys) with job objects(value) in this class and retrieve a job based on its unique ID.
// we can think of it as digital dictionary where we look up a definition (value) using a word (key).

  public class JobController {

      private static HashMap<Integer, Job> jobs = new HashMap<>(); // Store jobs with jobID as key

      public static List<Job> getJobsByMunicipality(String municipality) {
          List<Job> filteredJobs = new ArrayList<>();   //  filteredJobs is a local variable that hold the jobs that belong to the specified municipality
          for (Job job : jobs.values()) { // here we use a for-each loop to go through every job in the job.values().
              // Job job is loop variable thaat temporary holds the current Job object from collection during each iteration of the loop.
              if (job.getMunicipality().equalsIgnoreCase(municipality)) {
                  filteredJobs.add(job);
              }
          }
          return filteredJobs;
      }


      public static List<Job> searchJobs(String keyword) {
          List<Job> matchingJobs = new ArrayList<>();
          for (Job job : jobs.values()) {
              if (job.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                      job.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                  matchingJobs.add(job);
              }
          }
          return matchingJobs;
      }


      public static void createJob(Job job) {  //
          if (job == null || jobs.containsKey(job.getJobId())) { // This checks if the (job) parameter is null(no job was provided) and if either condition is true, the code will execude.
              //  jobs.containsKey(job.getJobId()) will check if a job with the same ID already exist in the (jobs)
              System.out.println("Job is null or already exists.");
              return;
          }
          jobs.put(job.getJobId(), job); // this adds the job to the Jobs Hashmap(data Structure)  with the help of job's ID as the key and the job object as the value.
          System.out.println("Job created successfully.");

          try
          {
              if (job==null){
                  throw new IllegalAccessException("Job can not be null");  //  IllegalAccessException is a build-in java exception class and it is part of java.lang package.
                  // we use it to check the validity of input parameters passed to method
              }
              if (jobs.containsKey(job.getJobId())){
                  throw new IllegalAccessException("Job ID already exist.");
              }
              jobs.put(job.getJobId(), job);
              System.out.println("Job created successfuly.");

          }  catch (IllegalAccessException e){
              System.out.println("Error creating job : " + e.getMessage());
          }
      }

      public static void updateJob(int jobID, Job updatedJob) {
          if (jobs.containsKey(jobID)) {
              jobs.put(jobID, updatedJob);
              System.out.println("Job updated successfully.");
          } else {
              System.out.println("Job with ID " + jobID + " does not exist.");
          }
          try
          {
              if (!jobs.containsKey(jobID)){
                  throw new NoSuchElementException("Job with ID " +jobID+ " does not exist");
              }
              if (updatedJob==null){
                  throw  new IllegalArgumentException(" Updated job can not be nul");
              }
              jobs.put(jobID, updatedJob);
              System.out.println(" Job updated successfully ");

          }catch (NoSuchElementException e){
              System.out.println("Error updating job: " + e.getMessage());

          }
      }

      public static void deleteJob(int jobID) {
          if (jobs.remove(jobID) != null) {
              System.out.println("Job deleted successfully.");
          } else {
              System.out.println("Job with ID " + jobID + " does not exist.");
          }
          try
          {
              if (!jobs.containsKey(jobID)){
                  throw new NoSuchElementException(" Job with ID " + jobID + "does not exist.");
              }
              jobs.remove(jobID);
              System.out.println("Job deleted successfully ");
          } catch (NoSuchElementException e){
              System.out.println(" Error deletig job" + e.getMessage());
          }

      }

      public static void markJobAsClosed(int jobID) {
          Job job = jobs.get(jobID);
          if (job != null) {
              job.setActive(false);
              System.out.println("Job marked as closed.");
          } else {
              System.out.println("Job with ID " + jobID + " does not exist.");
          }
      }

      public static Job getJobByID(int jobID) {
          return null;

      }
  }




