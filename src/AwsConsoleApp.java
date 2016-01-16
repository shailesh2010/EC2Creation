/*
 * Copyright 2010-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AuthorizeSecurityGroupIngressRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.CreateSecurityGroupRequest;
import com.amazonaws.services.ec2.model.CreateSecurityGroupResult;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.DescribeKeyPairsResult;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.IpPermission;
import com.amazonaws.services.ec2.model.KeyPair;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
/**
 * Welcome to your new AWS Java SDK based project!
 *
 * This class is meant as a starting point for your console-based application that
 * makes one or more calls to the AWS services supported by the Java SDK, such as EC2,
 * SimpleDB, and S3.
 *
 * In order to use the services in this sample, you need:
 *
 *  - A valid Amazon Web Services account. You can register for AWS at:
 *       https://aws-portal.amazon.com/gp/aws/developer/registration/index.html
 *
 *  - Your account's Access Key ID and Secret Access Key:
 *       http://aws.amazon.com/security-credentials
 *
 *  - A subscription to Amazon EC2. You can sign up for EC2 at:
 *       http://aws.amazon.com/ec2/
 *
 *  - A subscription to Amazon SimpleDB. You can sign up for Simple DB at:
 *       http://aws.amazon.com/simpledb/
 *
 *  - A subscription to Amazon S3. You can sign up for S3 at:
 *       http://aws.amazon.com/s3/
 */
public class AwsConsoleApp {
  
    static AmazonEC2      ec2;

    private static void init() throws Exception {

        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/shailesh/.aws/credentials), and is in valid format.",
                    e);
        }
        ec2 = new AmazonEC2Client(credentials);
    }


    public static void main(String[] args) throws Exception {

        System.out.println("===========================================");
        System.out.println("Welcome to the AWS Java SDK!");
        System.out.println("===========================================");

        init();
try {
	ec2.setEndpoint("ec2.us-west-2.amazonaws.com");
        	/*********************************************
        	 * 
             *  #2 Describe Availability Zones.
             *  
             *********************************************/
//        	System.out.println("#2 Describe Availability Zones.");
//            DescribeAvailabilityZonesResult availabilityZonesResult = ec2.describeAvailabilityZones();
//            System.out.println("You have access to " + availabilityZonesResult.getAvailabilityZones().size() +
//                    " Availability Zones.");

            /*********************************************
             * 
             *  #3 Describe Available Images
             *  
             *********************************************/
//            System.out.println("#3 Describe Available Images");
//            DescribeImagesResult dir = ec2.describeImages();
//            List<Image> images = dir.getImages();
//            System.out.println("You have " + images.size() + " Amazon images");
            
            CreateSecurityGroupRequest csgr = new CreateSecurityGroupRequest();
            csgr.withGroupName("shaileshsecuritygroup").withDescription("My security group");
            CreateSecurityGroupResult createSecurityGroupResult = 
            		  ec2.createSecurityGroup(csgr);
            IpPermission ipPermission1 = 
            		new IpPermission();
            	    	
            	ipPermission1.withIpRanges("0.0.0.0/0")
            	            .withIpProtocol("tcp")
            	            .withFromPort(22)
            	            .withToPort(22);
            	
            	IpPermission ipPermission2 = 
                		new IpPermission();
                	    	
                	ipPermission2.withIpRanges("0.0.0.0/0")
                	            .withIpProtocol("tcp")
                	            .withFromPort(80)
                	            .withToPort(80);
                	
                	IpPermission ipPermission3 = 
                    		new IpPermission();
                    	    	
                    	ipPermission3.withIpRanges("0.0.0.0/0")
                    	            .withIpProtocol("tcp")
                    	            .withFromPort(443)
                    	            .withToPort(443);
            	List<IpPermission> ipPermissions=new LinkedList<IpPermission>();
            	ipPermissions.add(ipPermission1);
            	ipPermissions.add(ipPermission2);
            	ipPermissions.add(ipPermission3);
            	AuthorizeSecurityGroupIngressRequest authorizeSecurityGroupIngressRequest =
            			new AuthorizeSecurityGroupIngressRequest();
            		    	
            		authorizeSecurityGroupIngressRequest.withGroupName("shaileshsecuritygroup").withIpPermissions(ipPermissions );

            		ec2.authorizeSecurityGroupIngress(authorizeSecurityGroupIngressRequest);
            		CreateKeyPairRequest createKeyPairRequest = new CreateKeyPairRequest();

            		createKeyPairRequest.withKeyName("shaileshkey");
            		CreateKeyPairResult createKeyPairResult =
            				  ec2.createKeyPair(createKeyPairRequest);
            		KeyPair keyPair = new KeyPair();

            		keyPair = createKeyPairResult.getKeyPair();

            		String privateKey = keyPair.getKeyMaterial();
            		File f= new File("Shaileshkey.pem");
            		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
            		writer.write(privateKey);
                    writer.close();
                    System.out.println("\n private key starts"+"\n"+privateKey+"\n"+"private key ends");
            /*********************************************
             *                 
             *  #4 Describe Key Pair
             *                 
             *********************************************/
//            System.out.println("#9 Describe Key Pair");
//            DescribeKeyPairsResult dkr = ec2.describeKeyPairs();
//            System.out.println(dkr.toString());
            
         
            
            /*********************************************
             * 
             *  #6 Create an Instance
             *  
             *********************************************/
            System.out.println("#5 Create an Instance");
            String imageId = "ami-9ff7e8af"; //Basic 32-bit Amazon Linux AMI
            int minInstanceCount = 1; // create 1 instance
            int maxInstanceCount = 1;
            RunInstancesRequest rir = new RunInstancesRequest(imageId, minInstanceCount, maxInstanceCount);
            rir.withKeyName("shaileshkey");
            rir.withSecurityGroups("shaileshsecuritygroup");
            rir.withInstanceType("t2.micro");
            RunInstancesResult result = ec2.runInstances(rir);
            
            //get instanceId from the result
            List<Instance> resultInstance = result.getReservation().getInstances();
            String createdInstanceId = null;
            for (Instance ins : resultInstance){
            	createdInstanceId = ins.getInstanceId();
            	System.out.println("New instance has been created: "+ins.getInstanceId());
            }
            
            
            /*********************************************
             * 
             *  #7 Create a 'tag' for the new instance.
             *  
             *********************************************/
            System.out.println("#6 Create a 'tag' for the new instance.");
            List<String> resources = new LinkedList<String>();
            List<Tag> tags = new LinkedList<Tag>();
            Tag nameTag = new Tag("Name", "MyFirstInstance");
            
            resources.add(createdInstanceId);
            tags.add(nameTag);
            
            CreateTagsRequest ctr = new CreateTagsRequest(resources, tags);
            ec2.createTags(ctr);
            
            
                        
            /*********************************************
             * 
             *  #8 Stop/Start an Instance
             *  
             *********************************************/
            System.out.println("#7 Stop the Instance");
            List<String> instanceIds = new LinkedList<String>();
            instanceIds.add(createdInstanceId);
            
            //stop
            //StopInstancesRequest stopIR = new StopInstancesRequest(instanceIds);
            //ec2.stopInstances(stopIR);
            
            //start
            //StartInstancesRequest startIR = new StartInstancesRequest(instanceIds);
            //ec2.startInstances(startIR);
            
            /*********************************************
             * 
             *  #5 Describe Current Instances
             *  
             *********************************************/
            System.out.println("#4 Describe Current Instances");
            DescribeInstancesResult describeInstancesRequest = ec2.describeInstances();
            List<Reservation> reservations = describeInstancesRequest.getReservations();
            Set<Instance> instances = new HashSet<Instance>();
            // add all instances to a Set.
            for (Reservation reservation : reservations) {
            	instances.addAll(reservation.getInstances());
            }
            
            System.out.println("You have " + instances.size() + " Amazon EC2 instance(s).");
            for (Instance ins : instances){
            	
            	// instance id
            	String instanceId = ins.getInstanceId();
            	
            	// instance state
            	InstanceState is = ins.getState();
            	String instancepublicdnsname=ins.getPublicDnsName();
            	String privateipaddress=ins.getPrivateIpAddress();
            	System.out.println(instanceId+" "+is.getName()+" "+instancepublicdnsname+" "+privateipaddress);
            }
            
            
            /*********************************************
             * 
             *  #9 Terminate an Instance
             *  
             *********************************************/
//            System.out.println("#8 Terminate the Instance");
//            TerminateInstancesRequest tir = new TerminateInstancesRequest(instanceIds);
            //ec2.terminateInstances(tir);
            
                        
            /*********************************************
             *  
             *  #10 shutdown client object
             *  
             *********************************************/
//            ec2.shutdown();
            
            
            
        } catch (AmazonServiceException ase) {
                System.out.println("Caught Exception: " + ase.getMessage());
                System.out.println("Reponse Status Code: " + ase.getStatusCode());
                System.out.println("Error Code: " + ase.getErrorCode());
                System.out.println("Request ID: " + ase.getRequestId());
        }

       
    }
}
