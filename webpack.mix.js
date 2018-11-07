const mix = require('laravel-mix');

mix.js('src/main/resources/assets/js/app.js', 'src/main/resources/static/assets/js')
    .copy('node_modules/font-awesome/fonts/', 'src/main/resources/static/fonts/vendor/font-awesome')
    .sass('src/main/resources/assets/sass/app.scss', 'src/main/resources/static/assets/css');