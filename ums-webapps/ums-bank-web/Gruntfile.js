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
        sync: {
            main: {
                files: [{
                    cwd: 'src/main/webapp/',
                    src: ['**/*.*'],
                    dest: '<%= pack.dest %>/'
                }// makes all src relative to cwd
                ]
            }
        },
        esteWatch: {
            options: {
                dirs: ['../ums-web-core/src/main/ts/**/',
                    'src/main/ts/**/',
                    'src/main/webapp/**/',
                    '!src/main/webapp/templates/**/',
                    '!src/main/webapp/vendors/**/']
            },
            ts: function (filepath) {
                return ['ts'];
            },
            html: function (filePath) {
                console.log("sync is done");
                return ['sync'];
            },
            css: function (filePath) {
                console.log("sync is done");
                return ['sync'];
            },
            js: function (filePath) {
                console.log("sync is done");
                return ['sync'];
            }
        },
        concurrent: {
            target: {
                tasks: ['watch'],
                options: {
                    logConcurrentOutput: true
                }
            }
        }
    });
    grunt.loadNpmTasks("grunt-ts");
    grunt.loadNpmTasks("grunt-concurrent");
    grunt.loadNpmTasks('grunt-este-watch');
    grunt.loadNpmTasks('grunt-sync');


    grunt.registerTask("default", ["sync", "ts", "concurrent"]);
};