package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobData;
import org.launchcode.models.forms.JobForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam int id) {


        Job jobDetail;
        jobDetail = jobData.findById(id);

        model.addAttribute( "DaJob",jobDetail );

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors/*,@RequestParam(value="formEmployer") int emp,@RequestParam(value="formSkill") int skill,@RequestParam(value="positionTypes") int pos*/) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if(errors.hasErrors() ){
            System.out.println("ERROR   ***********************");
            System.out.println(errors);
            model.addAttribute("errors", errors);
            //return "redirect:/job/add";
            return "new-job";
        }

        System.out.println( jobForm.getName() );
        System.out.println( jobForm.getLocation() );

        Job job = new Job( jobForm.getName(),
                jobData.getEmployers().findById(jobForm.getEmployerId()),
                jobData.getLocations().findById(jobForm.getLocation()),
                jobData.getPositionTypes().findById(jobForm.getPositionType()),
                jobData.getCoreCompetencies().findById(jobForm.getCoreCompetency())
        );



        jobData.add(job);


        System.out.println(job.getId());
        String reroute = "redirect:/job?id="+job.getId();
        return  reroute;
        //return "redirect:/list/all";

    }
}
