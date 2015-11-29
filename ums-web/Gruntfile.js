"use strict";

module.exports = function (grunt) {
    // Project configuration.
    grunt.initConfig({
        pack: grunt.file.readJSON('package.json'),
        ts: {
            default: {
                src: ['**/*.ts', "!node_modules/**/*.ts"],
                out: '<%= pack.dest %>/iums/js/main.js'
            }
        },
        watch: {
            options: {
                livereload: true
            },
            static_resource: {
                files: ['**/*.html', '**/*.css', '**/*.jpg', '**/*.gif', '**/*.png'],
                tasks: ['sync']
            },
            ts: {
                files: ['**/*.ts'],
                tasks: ['ts'],
                options: {
                    nospawn: true
                }
            },
            gruntfile: {
                files: 'Gruntfile.js',
                tasks: ['default']
            }
        },
        concurrent: {
            target: {
                tasks: ['watch'],
                options: {
                    logConcurrentOutput: true
                }
            }
        },
        sync: {
            main: {
                files: [{
                    cwd: 'src/main/webapp/iums/',
                    src: ['**/*.html', '**/*.css', '**/*.jpg', '**/*.gif', '**/*.png'],
                    dest: '<%= pack.dest %>/iums/'
                }// makes all src relative to cwd
                ]
            }
        }
    });
    grunt.loadNpmTasks("grunt-ts");
    grunt.loadNpmTasks("grunt-concurrent");
    grunt.loadNpmTasks('grunt-contrib-watch');
    grunt.loadNpmTasks('grunt-sync');


    grunt.registerTask("default", ["sync", "ts", "concurrent"]);
};