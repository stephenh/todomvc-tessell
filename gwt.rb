# A quick extension to use RJB with the Google Web Toolkit Generator:
# gwt dependencies

module Buildr
  module GWT
    include Extension

    def gwt(appName, options = {})
      task = GWTGenerateTask.define_task(path_to(:target, :generated, :gwt, appName))
      task.send :associate_with, self
      task.app_name = appName
      task.with(options)

      project.package(:war).include(:from => path_to(:target, :generated, :gwt, appName))
      project.task('package').enhance [task]

      task
    end

    class GWTGenerateTask < Rake::FileTask

       attr_accessor :style, :app_name, :max_memory, :args, :jvmargs, :classpath
       attr_reader :project

       def initialize(*args2) #:nodoc:
          super
          @args = []
          enhance do
            Buildr.ant('gwt:generate') do |ant|
              artifacts = project.compile.dependencies

              refid = "#{project.name}-gwt-classpath"
              ant.path :id => refid do
                [project.path_to(:src, :main, :java), project.path_to(:target, :classes), project.path_to(:target, :generated, :java), artifacts].flatten.each do |elt|
                  ant.pathelement :location => elt
                end
              end

              ant.java :fork => true, :failonerror => true, :classname =>'com.google.gwt.dev.Compiler' do
                if style
                  ant.arg :value => "-style"
                  ant.arg :value => style
                end

                args.each { |arg| ant.arg :value => arg }

                ant.arg :value => "-war"
                ant.arg :value => to_s

                ant.jvmarg :value => "-Xmx#{max_memory}"
                jvmargs.each { |arg| ant.jvmarg :value => arg }

                ant.classpath :refid => refid
                ant.arg :value => app_name
              end
            end
          end
        end

        # :call-seq:
        #   with(options) => self
        #
        # Passes options to the task and returns self. 
        #
        def with(options)
          options.each do |key, value|
            begin
              send "#{key}=", value
            rescue NoMethodError
              raise ArgumentError, "#{self.class.name} does not support the option #{key}"
            end
          end
          self
        end

        private

        def associate_with(project)
          @project = project
        end
    end
  end
end

class Buildr::Project
  include Buildr::GWT
end

