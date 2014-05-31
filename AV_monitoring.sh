<<<<<<< HEAD
#!/usr/local/bin/bash
=======
#!/usr/bin/bash
>>>>>>> refs/remotes/origin/master

#SCRIPT: AV_monitoring.sh
#
#AUTHOR: Guy Moskovich & Alina Vainshtein
#Date: 02/12/2009
#PLATFORM:AIX, HP-UX, Linux, Solaris
#
#PURPOSE: Monitoring tool for all Unix flavors
#		  using "vmstat" and "ps".
#
#Write the process you wish to monitor as a parameter,
#example: ./AV_monitoring.sh mds mdis
#
### Stop the monitoring script(usage: ./AV_monitoring.sh kill) ###
#
#
#
#      	
######## VARIABLES ##########
#############################
#
os=$(uname) # Defines the Unix flavor
machine=$(uname -a) #Defines the current machine
runningProcess=mds-r
curr=`date -u '+%d-%m-%Y_%H-%M-%S'`
#mdsProcessFileName=(mds_mon_${curr}.txt)
vmstatFileName=(vmstat_mon_${curr}.txt)
#
#mdRunningProcess=mdss-r
mdRunningProcess1=""
Process1FileName=""
#
mdRunningProcess2=""
Process2FileName=""
#
mdRunningProcess3=""
Process3FileName=""
#
numOfParam=""
secs=5
#
instance=""				#mdm instance name
#
#############################
############################################  FUNCTIONS ###########################################
function createHeader()
{
	if [ "$os" == "HP-UX" ]
	 then
		case $numOfParam in
		1)
			#printf "\n" >> ${Process1FileName}
		 	(export UNIX_STD=2003;$PS | head -1; unset UNIX_STD) >> ${Process1FileName}
			;;
		2)
			#printf "\n" >> ${Process1FileName}
			(export UNIX_STD=2003;$PS | head -1; unset UNIX_STD) >> ${Process1FileName}
			#printf "\n" >> ${Process2FileName}
			(export UNIX_STD=2003;$PS | head -1; unset UNIX_STD) >> ${Process2FileName}
			;;
		0|3|4)
			#printf "\n" >> ${Process1FileName}
			(export UNIX_STD=2003;$PS | head -1; unset UNIX_STD) >> ${Process1FileName}
			#printf "\n" >> ${Process2FileName}
			(export UNIX_STD=2003;$PS | head -1; unset UNIX_STD) >> ${Process2FileName}
			#printf "\n" >> ${Process3FileName}
			(export UNIX_STD=2003;$PS | head -1; unset UNIX_STD) >> ${Process3FileName}
			;;
		esac
	  else
		case $numOfParam in
		1)
			#printf "\n" >> ${Process1FileName}
		 	$PS | head -1 >> ${Process1FileName}
			;;
		2)		
			#printf "\n" >> ${Process1FileName}
			$PS | head -1 >> ${Process1FileName}
			#printf "\n" >> ${Process2FileName}
			$PS | head -1 >> ${Process2FileName}
			;;
		0|3|4)	
			#printf "\n" >> ${Process1FileName}
			$PS | head -1 >> ${Process1FileName}
			#printf "\n" >> ${Process2FileName}
			$PS | head -1 >> ${Process2FileName}
			#printf "\n" >> ${Process3FileName}	
			$PS | head -1 >> ${Process3FileName}
			;;	
		esac
	fi
}

#####################################################################################################		
#if ( [ $# -eq 1 ] ) ; then
	#if( [ $1 == "kill" ] ); then
		 # Kill all child processes
		function killMon
		{
			declare -a killarr

			line=`ps -ef | grep vmstat |grep -v grep`
			killarr=(`echo "$line"`)
			kill ${killarr[1]}

			vmstatLogFile=`ls -l vmstat*.* | awk '{print $NF}'`
			printf "\n\n$(date) - Stopped collecting logs -\n" >> ${vmstatLogFile}
			


			line=`ps -ef | grep AV_monitoring.sh | grep -v kill | grep -v grep`
			#echo $line
			killarr=(`echo "$line"`)
			kill ${killarr[1]}
			
			##write end-time to log-files:
			declare -a processArr
			i=0
			while read line
			do
			  processArr[i]=$line
			  (( i++ ))
			done <processes.tmp

			case ${processArr[0]} in
		1)
			processFile1=`ls -l ${processArr[1]}_mon*.txt* | awk '{print $NF}'`
			printf "\n$(date) - Stopped collecting logs -\n" >> ${processFile1}
			;;
		2)
			processFile1=`ls -l ${processArr[1]}_mon*.txt* | awk '{print $NF}'`
			printf "\n$(date) - Stopped collecting logs -\n" >> ${processFile1}
			processFile2=`ls -l ${processArr[2]}_mon*.txt* | awk '{print $NF}'`
			printf "\n$(date) - Stopped collecting logs -\n" >> ${processFile2}
			;;
		0|3|4)
			mdsFile=`ls -l mds_*mon*.* | awk '{print $NF}'`
			printf "\n$(date) - Stopped collecting logs -\n" >> ${mdsFile}

			processFile1=`ls -l mdis_*mon*.* | awk '{print $NF}'`

			printf "\n$(date) - Stopped collecting logs -\n" >> ${processFile1}

			processFile2=`ls -l mdss_*mon*.* | awk '{print $NF}'`
			printf "\n$(date) - Stopped collecting logs -\n" >> ${processFile2}
			;;
			esac

			rm -f processes.tmp	
		
			echo "--------------------------------------"
			echo "-	$(date) -" 
			echo "-       Stopped collecting logs      -"
			echo "--------------------------------------"
			exit
			#################
		}
		
		
#####################################################################################################		

function mds_proc  # mds process for all Unix flavors
{
	##	while (( RC==0 )) # Loop until the return code is not zero
		
		if [ "$os" == "HP-UX" -a "$instance" != "" ]             ## With Instance
		then 
		 while((1==1))
		 do
		      case $numOfParam in
		     1)
			 (export UNIX_STD=2003;$PS | grep ${mdRunningProcess1} | grep ${instance} | grep -v grep ; unset UNIX_STD) >> ${Process1FileName}
			 ;;
		     2)
			 (export UNIX_STD=2003;$PS | grep ${mdRunningProcess1} | grep ${instance} | grep -v grep ; unset UNIX_STD) >> ${Process1FileName}
			 (export UNIX_STD=2003;$PS | grep ${mdRunningProcess2} | grep ${instance} | grep -v grep ; unset UNIX_STD) >> ${Process2FileName} 
			 ;;
		   0|3)
			(export UNIX_STD=2003;$PS | grep ${mdRunningProcess1} | grep ${instance} | grep -v grep; unset UNIX_STD) >> ${Process1FileName}
			(export UNIX_STD=2003;$PS | grep ${mdRunningProcess2} | grep ${instance} | grep -v grep; unset UNIX_STD) >> ${Process2FileName}
			(export UNIX_STD=2003;$PS | grep ${mdRunningProcess3} | grep ${instance} | grep -v grep ; unset UNIX_STD) >> ${Process3FileName}
			;;	
		    esac			
		   sleep $secs
		 done
		 
		 else		
		 if [ "$os" == "HP-UX" -a "$instance" == "" ]		## Without Instance
		then
		 while((1==1))
		 do
		      case $numOfParam in

		     1)
			 (export UNIX_STD=2003;$PS | grep ${mdRunningProcess1}  | grep -v grep ; unset UNIX_STD) >> ${Process1FileName}
			 ;;
		     2)
			 (export UNIX_STD=2003;$PS | grep ${mdRunningProcess1}  | grep -v grep ; unset UNIX_STD) >> ${Process1FileName}
			 (export UNIX_STD=2003;$PS | grep ${mdRunningProcess2}  | grep -v grep ; unset UNIX_STD) >> ${Process2FileName} 
			 ;;
		   0|3)
			(export UNIX_STD=2003;$PS | grep ${mdRunningProcess1}  | grep -v grep; unset UNIX_STD) >> ${Process1FileName}
			(export UNIX_STD=2003;$PS | grep ${mdRunningProcess2}  | grep -v grep; unset UNIX_STD) >> ${Process2FileName}
			(export UNIX_STD=2003;$PS | grep ${mdRunningProcess3}  | grep -v grep ; unset UNIX_STD) >> ${Process3FileName}
			;;	
		    esac			
		   sleep $secs
		 done
		 
		else 
			if [ "$instance" != "" ]			## With Instance
				then 
			
		 while((1==1))
		 do
		     case $numOfParam in
		     1)
			 $PS | grep ${mdRunningProcess1} | grep ${instance} | grep -v grep >> ${Process1FileName}
			;;
		     2)
		     $PS | grep ${mdRunningProcess1} | grep ${instance} | grep -v grep  >> ${Process1FileName}
			 $PS | grep ${mdRunningProcess2} | grep ${instance} | grep -v grep  >> ${Process2FileName}
			;;
		     0|3)
			 $PS | grep ${mdRunningProcess1} | grep ${instance} | grep -v grep  >> ${Process1FileName}
			 $PS | grep ${mdRunningProcess2} | grep ${instance} | grep -v grep  >> ${Process2FileName}
			 $PS | grep ${mdRunningProcess3} | grep ${instance} | grep -v grep  >> ${Process3FileName}
			 ;;
		    esac
		   sleep $secs		
		 done
		 
		 else if [ "$instance" == "" ]			## Without Instance
		 then
	 	
		 while((1==1))
		 do
		     case $numOfParam in
		     1)
			 $PS | grep ${mdRunningProcess1} | grep -v grep >> ${Process1FileName}
			;;
		     2)
		     $PS | grep ${mdRunningProcess1}  | grep -v grep  >> ${Process1FileName}
			 $PS | grep ${mdRunningProcess2}  | grep -v grep  >> ${Process2FileName}
			;;
		     0|3)
			 $PS | grep ${mdRunningProcess1} | grep -v grep  >> ${Process1FileName}
			 $PS | grep ${mdRunningProcess2} | grep -v grep  >> ${Process2FileName}
			 $PS | grep ${mdRunningProcess3} | grep -v grep  >> ${Process3FileName}
			 ;;
		    esac
		   sleep $secs		
		 done
			
		fi
		fi
		fi
		fi
		 
}





#####################################################################################################		
#
#####################################################################################################
		function processMon1()
		{
			declare -a paramArr
			#paramArr[0]=$1
			if ( [ $numOfParam -eq 1 ] );then
			
				paramArr[0]=$process1
				process1=${paramArr[0]}-r
				Process1FileName=(${paramArr[$i]}_mon_${curr}.txt)
			
			else
				if ( [ $numOfParam -eq 2 ] );then
			
					paramArr[0]=$process1
					process1=${paramArr[0]}-r
					Process1FileName=(${paramArr[0]}_mon_${curr}.txt)
					
					paramArr[1]=$process2
					process2=${paramArr[1]}-r
					Process2FileName=(${paramArr[1]}_mon_${curr}.txt)

				else
					if ( [ $numOfParam -eq 3 ] );then
			
						paramArr[0]=$process1
						process1=${paramArr[0]}-r
						Process1FileName=(${paramArr[0]}_mon_${curr}.txt)

						paramArr[1]=$process2
						process2=${paramArr[1]}-r
						Process2FileName=(${paramArr[1]}_mon_${curr}.txt)
						
						paramArr[2]=$process3
						process3=${paramArr[2]}-r
						Process3FileName=(${paramArr[2]}_mon_${curr}.txt)				

 					fi
				fi	
			fi
		
		}

		function processMon1WithInstance()
		{
			declare -a paramArr
			#paramArr[0]=$1
			# if ( [ $numOfParam -eq 1 ] );then
			
				# paramArr[0]=$process1
				# process1=${paramArr[0]}-r
				# Process1FileName=(${paramArr[$i]}_mon_${instance}_${curr}.txt)
			
			# else
				if ( [ $numOfParam -eq 1 ] );then
			
					paramArr[0]=$process1
					process1=${paramArr[0]}-r
					Process1FileName=(${paramArr[0]}_mon_${instance}_${curr}.txt)
					
					# paramArr[1]=$process2
					# process2=${paramArr[1]}-r
					# Process2FileName=(${paramArr[1]}_mon_${instance}_${curr}.txt)

				 else
					if ( [ $numOfParam -eq 2 ] );then
			
						paramArr[0]=$process1
						process1=${paramArr[0]}-r
						Process1FileName=(${paramArr[0]}_mon_${instance}_${curr}.txt)

						paramArr[1]=$process2
						process2=${paramArr[1]}-r
						Process2FileName=(${paramArr[1]}_mon_${instance}_${curr}.txt)
						
						# paramArr[2]=$process3
						# process3=${paramArr[2]}-r
						# Process3FileName=(${paramArr[2]}_mon_${instance}_${curr}.txt)				
					else
						if ( [ $numOfParam -eq 3 ] );then
			
							paramArr[0]=$process1
							process1=${paramArr[0]}-r
							Process1FileName=(${paramArr[0]}_mon_${instance}_${curr}.txt)

							paramArr[1]=$process2
							process2=${paramArr[1]}-r
							Process2FileName=(${paramArr[1]}_mon_${instance}_${curr}.txt)
							
							paramArr[2]=$process3
							process3=${paramArr[2]}-r
							Process3FileName=(${paramArr[2]}_mon_${instance}_${curr}.txt)	
						fi

 					fi		
				fi	
		}

##############################################   MAIN   #######################################################		
###############################################################################################################		

if ( [ $# -ge 0 ] ) ; then


		if ( [ ${#@} -eq 0 ] );then

						numOfParam=0
						Process1FileName=(mds_mon_${curr}.txt)
						mdRunningProcess1=mds-r

						Process2FileName=(mdis_mon_${curr}.txt)
						mdRunningProcess2=mdis-r

						Process3FileName=(mdss_mon_${curr}.txt)							
						mdRunningProcess3=mdss-r
						
						#####write the processes to tmp file: 
			
						echo "0" >> processes.tmp
		else
			#kill:
			if( [ ${#@} -eq 1 -a  $1 == "kill" ] ); then
				
				killMon
				
			else
				if( [ ${#@} -eq 1 -a  $1 == "KILL" ] ); then
				
					killMon
				else 
				## 1 parameter  = Instance  -> numOfParam=0
				#############################################
					if [  ${#@} -eq 1  -a $1 != "mds" -a $1 != "mdis" -a $1 != "mdss" ]; then 
						numOfParam=0
						instance=$1
						Process1FileName=(mds_mon_${instance}_${curr}.txt)
						mdRunningProcess1=mds-r

						Process2FileName=(mdis_mon_${instance}_${curr}.txt)
						mdRunningProcess2=mdis-r

						Process3FileName=(mdss_mon_${instance}_${curr}.txt)							

						mdRunningProcess3=mdss-r
						
						#####write the processes to tmp file: 
			
						echo "0" >> processes.tmp
			
					else 
					## 1 parameter  - Process  -> numOfParam=1
					############################################
						if [ ${#@} -eq 1 ];then

							process1=$1
							numOfParam=1
							processName1=$1
							processMon1 "$process1" "$numOfParam"
							mdRunningProcess1=${process1}

							#write the processes to tmp file: 
							echo "1" >> processes.tmp
							echo "${processName1}" >> processes.tmp 
							
						else
							## 2 parameters  - With instance
							#################################
							if [ ${#@} -eq 2 -a $2 != "mds" -a $2 != "mdis" -a $2 != "mdss" ];then
							process1=$1
							instance=$2
							numOfParam=1
							processName1=$1
							##processName2=$2
							processMon1WithInstance "$process1" "$instance" "$numOfParam"
							mdRunningProcess1=${process1}
							##mdRunningProcess2=${process2}
							#write the processes to tmp file: 
							echo "1" >> processes.tmp
							echo "${processName1}" >> processes.tmp
							##echo "${processName2}" >> processes.tmp	
							else		
							
								## 2 parameters  - Without instance	
								#################################	
								if [  ${#@} -eq 2 ]; then
									process1=$1
									process2=$2
									numOfParam=2
									processName1=$1
									processName2=$2
									processMon1 "$process1" "$process2" "$numOfParam"
									mdRunningProcess1=${process1}
									mdRunningProcess2=${process2}

									#write the processes to tmp file: 
									echo "2" >> processes.tmp
									echo "${processName1}" >> processes.tmp
									echo "${processName2}" >> processes.tmp			
									
									else
									
											## 3 parameters  - With instance
											#################################
											if [ ${#@} -eq 3 -a $3 != "mds" -a $3 != "mdis" -a $3 != "mdss" ]; then
												process1=$1
												process2=$2
												##process3=$3
												instance=$3
												numOfParam=2
												processName1=$1
												processName2=$2
												processMon1WithInstance "$process1" "$process2" "$instance" "$numOfParam"
												mdRunningProcess1=${process1}
												mdRunningProcess2=${process2}	
												##mdRunningProcess3=${process3}
												#####write the processes to tmp file: 
												echo "2" >> processes.tmp
												echo "${processName1}" >> processes.tmp
												echo "${processName2}" >> processes.tmp	
												
												else 
													## 3 parameters  - Without instance
													if [ ${#@} -eq 3 ];then
														process1=$1
														process2=$2
														process3=$3
														numOfParam=3
														processName1=$1
														processName2=$2
														processName3=$3
														processMon1 "$process1" "$process2" "$process3" "$numOfParam"
														mdRunningProcess1=${process1}
														mdRunningProcess2=${process2}	
														mdRunningProcess3=${process3}
														#####write the processes to tmp file: 
														echo "3" >> processes.tmp
														echo "${processName1}" >> processes.tmp
														echo "${processName2}" >> processes.tmp	
														echo "${processName3}" >> processes.tmp	
														
													else
														## 3 parameters  - With instance
														#################################
														if ( [ ${#@} -eq 4 ] );then

															process1=$1
															process2=$2
															process3=$3
															instance=$4
															numOfParam=3
															processName1=$1
															processName2=$2
															processName3=$3
															processMon1WithInstance "$process1" "$process2" "$process3" "$instance" "$numOfParam"
															mdRunningProcess1=${process1}
															mdRunningProcess2=${process2}	
															mdRunningProcess3=${process3}
															#####write the processes to tmp file: 
															echo "3" >> processes.tmp
															echo "${processName1}" >> processes.tmp
															echo "${processName2}" >> processes.tmp	
															echo "${processName3}" >> processes.tmp	
														fi
													fi
												fi
											fi
										fi
									fi
								fi
							fi
						fi
					fi	
				fi
				
# Defines the number of seconds for each sample
#if ( [ $# -eq 0 ] ) ; then
#        secs=5
#else
#        if ( [ $# -eq 1 ] ) ; then
#                secs=$1
#        else
#                echo "Usage: `basename $0` [numberOfSeconds]"
#                #exit 1
#        fi
#fi

### CREATE OUT FILES FOR THE LOGS ###

# case $numOfParam in
# 1)
	# mdFile=$Process1FileName
	# mdProcess=$mdRunningProcess1
	# outFile "$mdFile" "$mdProcess"
	# ;;
# 2)
	# mdFile=$Process1FileName
	# mdProcess=$mdRunningProcess1
	# outFile "mdFile" "$mdProcess"
	
	# mdFile=$Process2FileName
	# mdProcess=$mdRunningProcess2
	# outFile "mdFile" "$mdProcess"
	# ;; 
# 0|3|4)
	# mdFile=$Process1FileName
	# mdProcess=$mdRunningProcess1
	# outFile "$mdFile" "$mdProcess"
	
	# mdFile=$Process2FileName
	# mdProcess=$mdRunningProcess2
	# outFile "$mdFile" "$mdProcess"
	
	# mdFile=$Process3FileName
	# mdProcess=$mdRunningProcess3
	# outFile "$mdFile" "mdProcess"
	# ;;
	
# esac

### SETUP THE ENVIROMENT FOR EACH OS ###		
		
case $os in
AIX)
		params="user,pid,pcpu,pmem,vsz,rssize,start,time,args"
		PS="ps -eo$params"
		
		createHeader "$numOfParam"
		mds_proc & # Call the function to start collecting ps logs
		;;

Linux)
		params="user,pid,pcpu,pmem,vsz,rssize,start,time,args"
		PS="ps -eo$params"
		
		createHeader "$numOfParam"
		mds_proc & # Call the function to start collecting ps logs
		;;

SunOS)		
		params="user,pid,pcpu,pmem,vsz,rss,stime,time,args"
		PS="ps -eo $params" 

		createHeader "$numOfParam"
		mds_proc & # Call the function to start collecting ps logs
		;;

HP-UX)
		params="user,pid,pcpu,vsz,stime,time,args"
		PS="ps -ae -o $params"
		
		createHeader "$numOfParam"
		mds_proc & # Call the function to start collecting ps logs
		;;
	
esac

### GATHERING STATISTICS ###
echo "---------------------------------------------------------------------------------------------------------------"
echo "-	Gathering CPU-Statistics using 'vmstat' into the file: ${vmstatFileName}                              -"
echo "-	Gathering process-info using 'ps' into txt files                                                      -"
echo "-	Parameters inputs should be entered in the following order  INSTANCE PROCESS1 PROCESS2 PROCESS3       -"
echo "---------------------------------------------------------------------------------------------------------------"
echo "----------------------------------------------------------"
echo "In order to Stop collection logs: ./AV_monitoring.sh kill"
echo "----------------------------------------------------------"
date -u > ${vmstatFileName}
chmod 775 ${vmstatFileName}
#printf "\nOperating System: $os\n" >> ${vmstatFileName}
#printf "Current Machine: $machine\n" >> ${vmstatFileName}
#printf "\n\n$(date) - Start collecting vmstat logs:\n" >> ${vmstatFileName}

#printf "\n" >> ${vmstatFileName}
(vmstat 5 >> ${vmstatFileName}) &
#${secs}
##while (( RC==0 )) # Loop until the return code is not zero
##do
	##ps -ef | grep ${runningProcess} | grep -v grep >/dev/null 2>&1
	
	##if [ $? -ne 0 ]; then
	# Kill all child processes
	##ps -f | awk '$3 == PID {print $2}' PID=$$ | xargs kill -15
	##echo "$(date) - Stoped collecting logs -" >> ${mdsProcessFileName}
	##echo "$(date) - Stoped collecting logs -" >> ${vmstatFileName}
	   ##echo "Done"
		##exit 
	##fi
##done		



