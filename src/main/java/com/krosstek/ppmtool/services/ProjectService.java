package com.krosstek.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krosstek.ppmtool.domain.BackLog;
import com.krosstek.ppmtool.domain.Project;
import com.krosstek.ppmtool.exceptions.ProjectIdException;
import com.krosstek.ppmtool.repositories.BacklogRepository;
import com.krosstek.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BacklogRepository backlogRepository;
	
	public Project saveOrUpdateProject(Project project) {
		
		try {
			String projectIdentifier = project.getProjectIdentifier().toUpperCase();
			project.setProjectIdentifier(projectIdentifier);
			
			if(project.getId()==null) {
				BackLog backLog = new BackLog();
				project.setBacklog(backLog);
				backLog.setProject(project);
				backLog.setProjectIdentifier(projectIdentifier);
			}
			if(project.getId()!=null) {
				project.setBacklog(backlogRepository.findByProjectIdentifier(projectIdentifier));
			}
			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException("Project Id '" + project.getProjectIdentifier().toUpperCase() + "' already exist!");
		}
	}
	public Project findProjectByIdentifier(String projectId) {
		
		Project project= projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(project==null) {
			throw new ProjectIdException("Project Id '" + projectId + "' does not exist!");
		}
		
		return project;
	}
	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}
	public void deleteProjectById(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(project==null) {
			throw new ProjectIdException("Project with Id '" + projectId + "' does not exist!");
		}
		
		projectRepository.delete(project);
	}
}
