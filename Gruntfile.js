module.exports = function(grunt) {

  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    jade: {
      compile: {
        options: {
          pretty : true,
          data: {
            debug: false,
          }
        },
        files: {
          "views_html/index.html": ["views/index.jade"]
        }
      }
    },
    injector: {
      options: {
        template : 'views_html/index.html',
      },
      bower_dependencies: {
        files: {
          'views_html/index.html': ['client/bower.json'],
        },
      }
    },
  });

  grunt.loadNpmTasks('grunt-contrib-jade');
  grunt.loadNpmTasks('grunt-injector');
  
  grunt.registerTask('default', ['jade','injector'])

};