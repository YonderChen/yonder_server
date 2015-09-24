require 'net/ssh'
require 'net/scp'

LOG_NUM = 30

def ant_build
  puts "[ANT BUILD]"
  result = `ant`
  puts result
end

def deploy(host, user, pwd, src_file, app_home, port, upload_lib)
  puts "[SSH #{host}:#{port}]"
  Net::SSH.start(host, user, :password => pwd, :port => port) do |ssh|
    puts "[UPLOAD DB FOLDER]"
    result = ssh.exec!("rm -rf #{app_home}/db/migration");
    puts result;
    result = ssh.exec!("mkdir -p #{app_home}/db/migration");
    puts result;
    last_name = "";
    ssh.scp.upload!("db/migration", "#{app_home}/db", :recursive => true) do |ch, name, sent, total|
      if(last_name != name)
        puts ""
        last_name = name;
      end
      print "\r#{name}: #{(sent.to_f * 100 / total.to_f).to_i}%"
    end
    puts ""

    if upload_lib      
      puts "[UPLOAD LIB FOLDER]"
      result = ssh.exec!("rm -rf #{app_home}/libs");
      puts result;
      result = ssh.exec!("mkdir -p #{app_home}/libs");
      puts result;
      last_name = "";
      ssh.scp.upload!("libs", "#{app_home}", :recursive => true) do |ch, name, sent, total|
        if(last_name != name)
          puts ""
          last_name = name;
        end
        print "\r#{name}: #{(sent.to_f * 100 / total.to_f).to_i}%"
      end
      puts ""
    end

    puts "[UPLOAD JAR]"
    ssh.scp.upload!(src_file, app_home) do |ch, name, sent, total|
      print "\r#{name}: #{(sent.to_f * 100 / total.to_f).to_i}%"
    end
    puts ""
    
    puts "[RESTART APP]"
    result = ssh.exec!("service game restart");
    puts result

    sleep(4);
    result = ssh.exec!("tail -n #{LOG_NUM} #{app_home}logs/game.log");
    puts result

  end
  puts "[DEPLOY SUCCESSFUL]"
end

@host = "192.168.2.19" #开发服 bj.appcup.com
@user = "root"
@pass = ""
@app_home = '/bjdata1/server/GameServer/'
#@port = 22011
@port = 22

@src_file = "dist/GameService.jar"
@upload_lib = false

puts "Host:#{@host}"
print "Do you need ant build (y/n):"
cmd = gets.chomp
if(cmd == "y")
  ant_build
end

print "Do you need upload Libs (y/n):"
cmd = gets.chomp
if(cmd == "y")
  @upload_lib = true
end

print "Password:"
@pass = gets.chomp

deploy(@host, @user, @pass, @src_file, @app_home, @port, @upload_lib)


