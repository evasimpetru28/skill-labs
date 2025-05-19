function initializeCharts(evaluationMonths, evaluationCounts, quizNames, quizScores) {
    // Evaluation Trends Chart
    const evaluationCtx = document.getElementById('evaluationTrendsChart').getContext('2d');
    const noEvaluationTrendsMessage = document.getElementById('noEvaluationTrendsMessage');
    
    if (!evaluationCounts || evaluationCounts.length === 0 || evaluationCounts.every(count => count === 0)) {
        evaluationCtx.canvas.style.display = 'none';
        noEvaluationTrendsMessage.style.display = 'block';
    } else {
        new Chart(evaluationCtx, {
            type: 'line',
            data: {
                labels: evaluationMonths,
                datasets: [{
                    label: 'Number of Evaluations',
                    data: evaluationCounts,
                    borderColor: '#4285f4',
                    tension: 0.4,
                    fill: false
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'top',
                    }
                }
            }
        });
    }

    // Quiz Performance Chart
    const quizCtx = document.getElementById('quizPerformanceChart').getContext('2d');
    const noQuizPerformanceMessage = document.getElementById('noQuizPerformanceMessage');
    
    if (!quizScores || quizScores.length === 0 || quizScores.every(score => score === 0)) {
        quizCtx.canvas.style.display = 'none';
        noQuizPerformanceMessage.style.display = 'block';
    } else {
        new Chart(quizCtx, {
            type: 'bar',
            data: {
                labels: quizNames,
                datasets: [{
                    label: 'Average Score',
                    data: quizScores,
                    backgroundColor: '#4285f4',
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'top',
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        max: 100
                    }
                }
            }
        });
    }
} 