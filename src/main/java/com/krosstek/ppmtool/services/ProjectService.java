package com.krosstek.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krosstek.ppmtool.domain.BackLog;
import com.krosstek.ppmtool.domain.Project;
import com.krosstek.ppmtool.exceptions.ProjectIdException;
import com.krosstek.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project) {
		
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			if(project.getId()==null) {
				BackLog backLog = new BackLog();
				project.setBackLog(backLog);
				backLog.setProject(project);
				backLog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
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
