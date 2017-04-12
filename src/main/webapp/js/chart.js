//Morris charts snippet - js

$.getScript('http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js',function(){
$.getScript('http://cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.0/morris.min.js',function(){

  Morris.Area({
    element: 'area-example',
    data: [
      { y: '2010', a: 60,  b: 40 },
      { y: '2011', a: 55,  b: 45 },
      { y: '2012', a: 50,  b: 50 },
      { y: '2013', a: 45,  b: 55 },
      { y: '2014', a: 40,  b: 60 },
      { y: '2015', a: 35,  b: 65 },
      { y: '2016', a: 30,  b: 70 }
    ],
    xkey: 'y',
    ykeys: ['a', 'b'],
    labels: ['Terrains non exploités %', 'Terrains exploités %']
  });
  
  Morris.Line({
        element: 'line-example',
        data: [
          {year: '2012', value: 100},
          {year: '2013', value: 125},
          {year: '2014', value: 110},
          {year: '2015', value: 140},
          {year: '2016', value: 115}
        ],
        xkey: 'year',
        ykeys: ['value'],
        labels: ['Tonnes']
      });
      
      Morris.Donut({
        element: 'donut-example',
        data: [
         {label: "Blé %", value: 30},
		 {label: "Olive %", value: 25},
         {label: "Datte %", value: 15},
		 {label: "Pomme %", value: 10},
		 {label: "Poisson %", value: 10},
		 {label: "Autres %", value: 10}
        ]
      });
      
      Morris.Bar({
         element: 'bar-example',
         data: [
            {y: 'Jan 2016', a: 180},
            {y: 'Fev 2016', a: 175},
            {y: 'Mar 2016', a: 150},
            {y: 'Avr 2016', a: 140},
            {y: 'Mai 2016', a: 135},
            {y: 'Jun 2016', a: 120}
         ],
         xkey: 'y',
         ykeys: ['a'],
         labels: ['Tonnes', 'Conversions']
      });
  
});
});