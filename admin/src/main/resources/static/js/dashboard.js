function initializeCharts(userStats, skillStats, evaluationStats) {
    // User Distribution Chart
    const userCtx = document.getElementById('userDistributionChart');
    if (userCtx) {
        new Chart(userCtx, {
            type: 'pie',
            data: {
                labels: ['Students', 'Professors', 'Companies', 'Admins'],
                datasets: [{
                    data: [
                        userStats.studentCount || 0,
                        userStats.professorCount || 0,
                        userStats.companyCount || 0,
                        userStats.adminCount || 0
                    ],
                    backgroundColor: [
                        'rgba(54, 162, 235, 0.8)',
                        'rgba(75, 192, 192, 0.8)',
                        'rgba(255, 159, 64, 0.8)',
                        'rgba(255, 99, 132, 0.8)'
                    ]
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'bottom',
                        labels: {
                            padding: 20
                        }
                    },
                    title: {
                        display: true,
                        text: 'User Distribution',
                        font: { size: 16 },
                        padding: 20
                    }
                }
            }
        });
    }

    // Skills by Category Chart
    const skillCtx = document.getElementById('skillsByCategoryChart');
    if (skillCtx) {
        new Chart(skillCtx, {
            type: 'bar',
            data: {
                labels: skillStats.categories || [],
                datasets: [{
                    label: 'Number of Skills',
                    data: skillStats.counts || [],
                    backgroundColor: 'rgba(75, 192, 192, 0.8)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false
                    },
                    title: {
                        display: true,
                        text: 'Skills Distribution by Category',
                        font: { size: 16 },
                        padding: 20
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1
                        }
                    }
                }
            }
        });
    }

    // Popular Skills Chart
    const popularCtx = document.getElementById('popularSkillsChart');
    if (popularCtx) {
        new Chart(popularCtx, {
            type: 'bar',
            data: {
                labels: evaluationStats.popularSkillNames || [],
                datasets: [{
                    label: 'Number of Evaluations',
                    data: evaluationStats.popularSkillCounts || [],
                    backgroundColor: 'rgba(54, 162, 235, 0.8)',
                    borderWidth: 1
                }]
            },
            options: {
                indexAxis: 'y',
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: false
                    },
                    title: {
                        display: true,
                        text: 'Most Popular Skills',
                        font: { size: 16 },
                        padding: 20
                    }
                },
                scales: {
                    x: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 1
                        }
                    }
                }
            }
        });
    }
} 