document.addEventListener('DOMContentLoaded', function() {
    try {
        // Initialize tooltips
        const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
        [...tooltipTriggerList].map(tooltipTriggerEl => new bootstrap.Tooltip(tooltipTriggerEl));

        console.log('Fetching dashboard data...');
        const superuserId = document.querySelector('main').dataset.superuserId;

        fetch(`/api/dashboard/stats/${superuserId}`)
            .then(response => response.json())
            .then(data => {
                console.log('Data received:', data);

                const { quizStats, skillStats, monthlyStats } = data;

                // Update quiz statistics
                document.getElementById('totalQuizzes').textContent = quizStats.totalQuizzes || 0;
                document.getElementById('myQuizzesAvgScore').textContent = quizStats.myQuizzesAvgScore + '%' || 0;
                document.getElementById('publicQuizzesAvgScore').textContent = quizStats.publicQuizzesAvgScore + '%' || 0;
                document.getElementById('uniqueQuizParticipants').textContent = quizStats.uniqueQuizParticipants || 0;
                document.getElementById('mostAttemptedQuiz').textContent = quizStats.mostAttemptedQuiz || '-';

                // Update completion rate
                const completionRate = skillStats.completionRate || 0;
                console.log(completionRate)
                document.getElementById('completionRate').textContent = completionRate + '%';
                document.getElementById('completionRateProgress').style.width = `${completionRate}%`;
                document.getElementById('completionRateProgress').setAttribute('aria-valuenow', completionRate);
                document.getElementById('completedQuizzesStudents').textContent = quizStats.completedQuizzesStudents || 0;
                document.getElementById('totalCompletionStudents').textContent = skillStats.completionTotalStudents || 0;

                // Update high performers statistics
                const highPerformersPercentage = skillStats.highPerformersPercentage || 0;
                const skillEvalRate = skillStats.skillEvalRate || 0;
                document.getElementById('skillEvalRate').textContent = (skillEvalRate || 0) + '%'
                document.getElementById('skillEvalRateCount').textContent = skillStats.skillEvalRateCount || 0
                document.getElementById('skillEvalRateTotal').textContent = quizStats.uniqueQuizParticipants || 0
                document.getElementById('skillEvalRateProgress').style.width = `${skillEvalRate}%`;
                document.getElementById('skillEvalRateProgress').setAttribute('aria-valuenow', skillEvalRate);
                document.getElementById('highPerformers').textContent = `${highPerformersPercentage}%`;
                document.getElementById('highPerformersProgress').style.width = `${highPerformersPercentage}%`;
                document.getElementById('highPerformersProgress').setAttribute('aria-valuenow', highPerformersPercentage);
                document.getElementById('highPerformersCount').textContent = skillStats.highPerformersCount || 0;
                document.getElementById('totalStudents').textContent = skillStats.totalStudents || 0;

                // Update mastery level distribution
                const beginnerCount = skillStats.beginnerCount || 0;
                const intermediateCount = skillStats.intermediateCount || 0;
                const advancedCount = skillStats.advancedCount || 0;
                const totalEvaluations = beginnerCount + intermediateCount + advancedCount;
                const beginnerPercentage = totalEvaluations > 0 ? (beginnerCount / totalEvaluations) * 100 : 0;

                const intermediatePercentage = totalEvaluations > 0 ? (intermediateCount / totalEvaluations) * 100 : 0;
                const advancedPercentage = totalEvaluations > 0 ? (advancedCount / totalEvaluations) * 100 : 0;

                document.getElementById('beginnerCount').textContent = beginnerCount;
                document.getElementById('intermediateCount').textContent = intermediateCount;
                document.getElementById('advancedCount').textContent = advancedCount;

                document.getElementById('beginnerProgress').style.width = `${beginnerPercentage}%`;
                document.getElementById('beginnerProgress').setAttribute('aria-valuenow', beginnerPercentage);

                document.getElementById('intermediateProgress').style.width = `${intermediatePercentage}%`;
                document.getElementById('intermediateProgress').setAttribute('aria-valuenow', intermediatePercentage);

                document.getElementById('advancedProgress').style.width = `${advancedPercentage}%`;
                document.getElementById('advancedProgress').setAttribute('aria-valuenow', advancedPercentage);

                // Update total students for mastery distribution
                document.getElementById('totalMasteryStudents').textContent = skillStats.totalStudents || totalEvaluations || 0;

                // Initialize charts
                initializeCharts(monthlyStats);
                console.log('Charts initialized successfully');
            })
            .catch(error => {
                console.error('Error fetching dashboard data:', error);
            });
    } catch (error) {
        console.error('Error initializing dashboard:', error);
    }
});

function initializeCharts(monthlyStats) {
    // Most Evaluated Skills Chart (Pie)
    const topSkillsCtx = document.getElementById('topSkillsChart').getContext('2d');
    new Chart(topSkillsCtx, {
        type: 'pie',
        data: {
            labels: monthlyStats.topSkillNames || [],
            datasets: [{
                data: monthlyStats.topSkillCounts || [],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 206, 86, 0.8)',
                    'rgba(75, 192, 192, 0.8)',
                    'rgba(153, 102, 255, 0.8)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    position: 'right'
                }
            }
        }
    });

    // Category Scores Chart (Bar)
    const categoryScoresCtx = document.getElementById('categoryScoresChart').getContext('2d');
    new Chart(categoryScoresCtx, {
        type: 'bar',
        data: {
            labels: monthlyStats.categoryNames || [],
            datasets: [{
                label: 'Average Score',
                data: monthlyStats.categoryAverages || [],
                backgroundColor: 'rgba(75, 192, 192, 0.8)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    max: 100
                }
            }
        }
    });

    // Monthly Quiz Completions Chart
    const monthlyQuizCompletionsCtx = document.getElementById('monthlyQuizCompletionsChart').getContext('2d');
    new Chart(monthlyQuizCompletionsCtx, {
        type: 'line',
        data: {
            labels: monthlyStats.months || [],
            datasets: [{
                label: 'Completions',
                data: monthlyStats.completions || [],
                borderColor: 'rgba(75, 192, 192, 1)',
                tension: 0.1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    // Quiz Score Distribution Chart
    const quizScoreDistributionCtx = document.getElementById('quizScoreDistributionChart').getContext('2d');
    new Chart(quizScoreDistributionCtx, {
        type: 'bar',
        data: {
            labels: ['0-20', '21-40', '41-60', '61-80', '81-100'],
            datasets: [{
                label: 'Number of Students',
                data: monthlyStats.scoreDistribution || [0, 0, 0, 0, 0],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.5)',
                    'rgba(255, 159, 64, 0.5)',
                    'rgba(255, 205, 86, 0.5)',
                    'rgba(75, 192, 192, 0.5)',
                    'rgba(54, 162, 235, 0.5)'
                ],
                borderColor: [
                    'rgb(255, 99, 132)',
                    'rgb(255, 159, 64)',
                    'rgb(255, 205, 86)',
                    'rgb(75, 192, 192)',
                    'rgb(54, 162, 235)'
                ],
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}